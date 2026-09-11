// src/components/Table/Table.jsx
import React, { useState } from 'react';
import { FiChevronUp, FiChevronDown, FiSearch } from 'react-icons/fi';

/**
 * Reusable Table Component with sorting
 */
const Table = ({
  columns,
  data,
  onRowClick,
  isLoading = false,
  emptyMessage = 'No data available',
  sortable = true,
}) => {
  const [sortConfig, setSortConfig] = useState({ key: null, direction: 'asc' });

  // Handle sorting
  const handleSort = (key) => {
    if (!sortable) return;

    let direction = 'asc';
    if (sortConfig.key === key && sortConfig.direction === 'asc') {
      direction = 'desc';
    }
    setSortConfig({ key, direction });
  };

  // Sort data
  const sortedData = React.useMemo(() => {
    if (!sortConfig.key) return data;

    const sorted = [...data].sort((a, b) => {
      const aValue = a[sortConfig.key];
      const bValue = b[sortConfig.key];

      if (aValue < bValue) return sortConfig.direction === 'asc' ? -1 : 1;
      if (aValue > bValue) return sortConfig.direction === 'asc' ? 1 : -1;
      return 0;
    });

    return sorted;
  }, [data, sortConfig]);

  if (isLoading) {
    return (
      <div className="space-y-3">
        {Array(5)
          .fill(0)
          .map((_, i) => (
            <div
              key={i}
              className="h-12 bg-gradient-to-r from-gray-200/50 to-gray-300/50 rounded animate-pulse"
            />
          ))}
      </div>
    );
  }

  return (
    <div className="overflow-x-auto bg-white/40 backdrop-blur-md rounded-xl border border-white/20 shadow-lg">
      <table className="w-full">
        {/* Header */}
        <thead className="bg-white/20 border-b border-white/10">
          <tr>
            {columns.map((column) => (
              <th
                key={column.key}
                onClick={() => handleSort(column.key)}
                className={`px-6 py-4 text-left text-sm font-semibold text-gray-900 ${
                  column.sortable !== false && sortable ? 'cursor-pointer hover:bg-white/10' : ''
                } transition-colors`}
              >
                <div className="flex items-center gap-2">
                  {column.label}
                  {column.sortable !== false && sortable && sortConfig.key === column.key && (
                    sortConfig.direction === 'asc' ? <FiChevronUp size={16} /> : <FiChevronDown size={16} />
                  )}
                </div>
              </th>
            ))}
          </tr>
        </thead>

        {/* Body */}
        <tbody>
          {sortedData.length === 0 ? (
            <tr>
              <td colSpan={columns.length} className="px-6 py-12 text-center text-gray-600">
                {emptyMessage}
              </td>
            </tr>
          ) : (
            sortedData.map((row, rowIdx) => (
              <tr
                key={rowIdx}
                onClick={() => onRowClick?.(row)}
                className={`border-t border-white/10 ${
                  onRowClick ? 'hover:bg-white/20 cursor-pointer' : ''
                } transition-colors`}
              >
                {columns.map((column) => (
                  <td
                    key={`${rowIdx}-${column.key}`}
                    className="px-6 py-4 text-sm text-gray-700"
                  >
                    {column.render
                      ? column.render(row[column.key], row)
                      : row[column.key]}
                  </td>
                ))}
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
};

export default Table;

// src/components/Table/DataTable.jsx
/**
 * Advanced Data Table with filtering, sorting, and pagination
 */
const DataTable = ({
  columns,
  data,
  onRowClick,
  searchable = true,
  paginate = true,
  itemsPerPage = 10,
}) => {
  const [searchTerm, setSearchTerm] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const [sortConfig, setSortConfig] = useState({ key: null, direction: 'asc' });

  // Filter data
  const filteredData = React.useMemo(() => {
    if (!searchTerm) return data;

    return data.filter(row =>
      Object.values(row).some(value =>
        String(value).toLowerCase().includes(searchTerm.toLowerCase())
      )
    );
  }, [data, searchTerm]);

  // Sort data
  const sortedData = React.useMemo(() => {
    if (!sortConfig.key) return filteredData;

    const sorted = [...filteredData].sort((a, b) => {
      const aValue = a[sortConfig.key];
      const bValue = b[sortConfig.key];

      if (aValue < bValue) return sortConfig.direction === 'asc' ? -1 : 1;
      if (aValue > bValue) return sortConfig.direction === 'asc' ? 1 : -1;
      return 0;
    });

    return sorted;
  }, [filteredData, sortConfig]);

  // Paginate data
  const paginatedData = React.useMemo(() => {
    if (!paginate) return sortedData;

    const startIndex = (currentPage - 1) * itemsPerPage;
    return sortedData.slice(startIndex, startIndex + itemsPerPage);
  }, [sortedData, currentPage, itemsPerPage, paginate]);

  const totalPages = Math.ceil(sortedData.length / itemsPerPage);

  const handleSort = (key) => {
    let direction = 'asc';
    if (sortConfig.key === key && sortConfig.direction === 'asc') {
      direction = 'desc';
    }
    setSortConfig({ key, direction });
    setCurrentPage(1);
  };

  return (
    <div className="space-y-6">
      {/* Search Bar */}
      {searchable && (
        <div className="relative">
          <FiSearch className="absolute left-4 top-1/2 -translate-y-1/2 text-gray-400" size={20} />
          <input
            type="text"
            placeholder="Search table..."
            value={searchTerm}
            onChange={(e) => {
              setSearchTerm(e.target.value);
              setCurrentPage(1);
            }}
            className="w-full pl-12 pr-4 py-3 bg-white/40 border border-white/20 rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-500"
          />
        </div>
      )}

      {/* Table */}
      <Table
        columns={columns}
        data={paginatedData}
        onRowClick={onRowClick}
        sortable={true}
      />

      {/* Pagination */}
      {paginate && totalPages > 1 && (
        <div className="flex-between">
          <p className="text-sm text-gray-600">
            Showing {(currentPage - 1) * itemsPerPage + 1} to{' '}
            {Math.min(currentPage * itemsPerPage, sortedData.length)} of {sortedData.length} results
          </p>

          <div className="flex gap-2">
            <button
              onClick={() => setCurrentPage(prev => Math.max(prev - 1, 1))}
              disabled={currentPage === 1}
              className="px-4 py-2 bg-white/40 border border-white/20 rounded-lg disabled:opacity-50 hover:bg-white/60 transition-all"
            >
              Previous
            </button>

            {Array.from({ length: totalPages }).map((_, i) => (
              <button
                key={i + 1}
                onClick={() => setCurrentPage(i + 1)}
                className={`px-3 py-2 rounded-lg transition-all ${
                  currentPage === i + 1
                    ? 'bg-purple-500 text-white'
                    : 'bg-white/40 border border-white/20 hover:bg-white/60'
                }`}
              >
                {i + 1}
              </button>
            ))}

            <button
              onClick={() => setCurrentPage(prev => Math.min(prev + 1, totalPages))}
              disabled={currentPage === totalPages}
              className="px-4 py-2 bg-white/40 border border-white/20 rounded-lg disabled:opacity-50 hover:bg-white/60 transition-all"
            >
              Next
            </button>
          </div>
        </div>
      )}

      {/* Results Summary */}
      <div className="text-center text-sm text-gray-600">
        {filteredData.length === 0 ? (
          <p>No results found</p>
        ) : (
          <p>
            Found {filteredData.length} result{filteredData.length !== 1 ? 's' : ''}
          </p>
        )}
      </div>
    </div>
  );
};

export { Table, DataTable };
