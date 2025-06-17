let filteredLicenses= [];
// Initialize the application
document.addEventListener('DOMContentLoaded', function() {
    updateTable();
    setupEventListeners();
    
    // Set default date range (last 6 months)
    const today = new Date();
    const sixMonthsAgo = new Date(today.getFullYear(), today.getMonth() - 6, today.getDate());
    
    document.getElementById('dateFrom').value = formatDateForInput(sixMonthsAgo);
    document.getElementById('dateTo').value = formatDateForInput(today);
});

// Setup event listeners
function setupEventListeners() {
    // Add row selection functionality
    document.addEventListener('click', function(e) {
        if (e.target.closest('tbody tr')) {
            const row = e.target.closest('tbody tr');
            const documento = row.cells[1].textContent;
            toggleRowSelection(row, documento);
        }
    });
}

// Search function
function searchLicenses() {
    alert('Buscando licencias no vigentes...');
    const dateFrom = document.getElementById('dateFrom').value;
    const dateTo = document.getElementById('dateTo').value;
    const classFilter = Array.from(document.getElementById('classSelect').selectedOptions)
        .map(option => option.value).filter(val => val !== '');

    // Build query parameters
    const params = new URLSearchParams();
    if (dateFrom) params.append('fechaDesde', dateFrom);
    if (dateTo) params.append('fechaHasta', dateTo);
    if (classFilter.length > 0) params.append('clase', classFilter.join(','));

    // Make AJAX request to the backend
    fetch(`/api/licencias/noVigentes?${params.toString()}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al buscar licencias no vigentes');
            }
            return response.json();
        })
        .then(data => {
            filteredLicenses = data; // Update filteredLicenses with the response data
            updateTable(); // Update the table with the new data
            showNotification(`Se encontraron ${filteredLicenses.length} licencias`, 'success');
        })
        .catch(error => {
            console.error(error);
            showNotification('Error al buscar licencias no vigentes', 'error');
        });

}

// Update table with filtered data
function updateTable() {
    const tableBody = document.getElementById('resultsTableBody');

    if (filteredLicenses.length === 0) {
        tableBody.innerHTML = `
            <tr>
                <td colspan="8" style="text-align: center; padding: 40px; color: #666;">
                    <div class="empty-state">
                        <h3>No se encontraron resultados</h3>
                        <p>Intente modificar los criterios de búsqueda</p>
                    </div>
                </td>
            </tr>
        `;
        return;
    }

    tableBody.innerHTML = filteredLicenses.map(license => `
        <tr data-documento="${license.documento}" class="selectable-row">
            <td>${license.nombreCompletoTitular}</td>
            <td>${license.nombreCompletoTitular}</td>
            <td>${license.clase}</td>
            <td>${license.fechaVencimiento}</td>
            <td>${license.estadoActual}</td>
        </tr>
    `).join('');

    // Add status badge styles
    addStatusBadgeStyles();
}

// Toggle row selection
function toggleRowSelection(row, documento) {
    if (selectedLicenses.has(documento)) {
        selectedLicenses.delete(documento);
        row.classList.remove('selected');
    } else {
        selectedLicenses.add(documento);
        row.classList.add('selected');
    }
    
    updateActionButtons();
}

// Update action buttons based on selection
function updateActionButtons() {
    const renewBtn = document.querySelector('.action-btn.primary');
    const exportBtn = document.querySelector('.action-btn.secondary');
    
    if (selectedLicenses.size > 0) {
        renewBtn.textContent = `Renovar (${selectedLicenses.size})`;
        exportBtn.textContent = `Emitir reporte (${selectedLicenses.size})`;
    } else {
        renewBtn.textContent = 'Renovar';
        exportBtn.textContent = 'Emitir reporte';
    }
}

// Renew selected licenses
function renewSelected() {
    if (selectedLicenses.size === 0) {
        showNotification('Seleccione al menos una licencia para renovar', 'error');
        return;
    }

    const confirmMessage = selectedLicenses.size === 1 
        ? '¿Está seguro de que desea renovar la licencia seleccionada?'
        : `¿Está seguro de que desea renovar las ${selectedLicenses.size} licencias seleccionadas?`;

    if (confirm(confirmMessage)) {
        // Here you would make the API call to your Spring Boot backend
        // Example: POST /api/licenses/renew with selectedLicenses array
        
        showNotification(`Iniciando renovación de ${selectedLicenses.size} licencia(s)`, 'success');
        
        // Clear selection
        selectedLicenses.clear();
        document.querySelectorAll('.selected').forEach(row => row.classList.remove('selected'));
        updateActionButtons();
        
        // In a real application, you would refresh the data from the server
        // refreshData();
    }
}

// Export data
function exportData() {
    const dataToExport = selectedLicenses.size > 0 
        ? filteredLicenses.filter(license => selectedLicenses.has(license.documento))
        : filteredLicenses;

    if (dataToExport.length === 0) {
        showNotification('No hay datos para exportar', 'error');
        return;
    }

    // Here you would make the API call to your Spring Boot backend
    // Example: GET /api/licenses/export?format=excel&documents=doc1,doc2,doc3
    
    showNotification(`Generando reporte de ${dataToExport.length} licencia(s)`, 'info');
    
    // Simulate file download
    setTimeout(() => {
        showNotification('Reporte generado exitosamente', 'success');
    }, 2000);
}

// Utility functions
function formatDate(dateString) {
    const date = new Date(dateString);
    return date.toLocaleDateString('es-AR', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
    });
}

function formatDateForInput(date) {
    return date.toISOString().split('T')[0];
}

function showNotification(message, type) {
    // Remove existing notifications
    document.querySelectorAll('.notification').forEach(n => n.remove());
    
    const notification = document.createElement('div');
    notification.className = `notification ${type}`;
    notification.textContent = message;
    
    document.body.appendChild(notification);
    
    setTimeout(() => {
        notification.remove();
    }, 4000);
}

function addStatusBadgeStyles() {
    if (!document.getElementById('statusStyles')) {
        const style = document.createElement('style');
        style.id = 'statusStyles';
        style.textContent = `
            .status-badge {
                padding: 4px 8px;
                border-radius: 12px;
                font-size: 11px;
                font-weight: 500;
                text-transform: uppercase;
            }
            
            .status-badge.vencida {
                background-color: #fee;
                color: #d63384;
                border: 1px solid #f5c2c7;
            }
            
            .selectable-row {
                cursor: pointer;
                transition: background-color 0.2s ease;
            }
            
            .selectable-row.selected {
                background-color: #e8f8f7 !important;
                border-left: 3px solid #4ECDC4;
            }
            
            .selectable-row:hover {
                background-color: #f8f9fa;
            }
        `;
        document.head.appendChild(style);
    }
}

// Clear all filters
function clearFilters() {
    document.getElementById('nameInput').value = '';
    document.getElementById('dateFrom').value = '';
    document.getElementById('dateTo').value = '';
    document.getElementById('classSelect').selectedIndex = -1;
    document.getElementById('statusSelect').selectedIndex = 0;
    document.getElementById('urgentOnly').checked = false;
    
    filteredLicenses = [...allLicenses];
    updateTable();
    showNotification('Filtros limpiados', 'info');
}