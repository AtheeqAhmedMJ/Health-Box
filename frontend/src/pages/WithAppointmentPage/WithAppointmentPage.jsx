import React, { useState } from 'react';
import { appointments } from './../../assets/data/appointments';
import './WithAppointmentPage.css';

const WithAppointmentPage = () => {
  const [search, setSearch] = useState('');
  const [currentPage, setCurrentPage] = useState(1);
  const pageSize = 10;

  const filtered = appointments.filter(
    (p) =>
      p.name.toLowerCase().includes(search.toLowerCase()) ||
      p.phone.includes(search)
  );

  const totalPages = Math.ceil(filtered.length / pageSize);
  const currentAppointments = filtered.slice(
    (currentPage - 1) * pageSize,
    currentPage * pageSize
  );

  return (
    <div className="with-appointment-page">
      <h1 className="page-title">WITH APPOINTMENTS</h1>

      <input
        type="text"
        placeholder="Search by name or phone..."
        value={search}
        onChange={(e) => {
          setSearch(e.target.value);
          setCurrentPage(1);
        }}
        className="search-bar"
      />

      <div className="appointment-container2">
        {currentAppointments.map((appt, index) => (
          <div key={appt.id} className="appointment-row">
            <div className="index-circle">{(currentPage - 1) * pageSize + index + 1}</div>
            <div className="name-age">{appt.name}, {appt.age}</div>
            <div className="arrow-icon">➔</div>
            <div className="status-icon">☑</div>
          </div>
        ))}
      </div>

      {totalPages > 1 && (
        <div className="pagination">
          {[...Array(totalPages)].map((_, i) => (
            <button
              key={i}
              className={`page-btn ${i + 1 === currentPage ? 'active' : ''}`}
              onClick={() => setCurrentPage(i + 1)}
            >
              {i + 1}
            </button>
          ))}
        </div>
      )}
    </div>
  );
};

export default WithAppointmentPage;
