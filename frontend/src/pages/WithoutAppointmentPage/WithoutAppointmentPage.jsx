import React, { useState } from 'react';
import './WithoutAppointmentPage.css';

const WithoutAppointmentPage = () => {
  const [formData, setFormData] = useState({
    name: '',
    date: '',
    age: '',
    sex: '',
  });

  const handleChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('Booking without appointment:', formData);
    // Optionally navigate to consultation panel with formData
  };

  return (
    <div className="without-appointment-page">
      <h1 className="WithoutAppointment">WITHOUT APPOINTMENTS</h1>

      <form className="appointment-form" onSubmit={handleSubmit}>
        <label>Patient Name</label>
        <input
          type="text"
          name="name"
          placeholder="Name"
          value={formData.name}
          onChange={handleChange}
          required
        />

        <div className="input-row">
          <div className="input-group">
            <label>Date</label>
            <input
              type="date"
              name="date"
              value={formData.date}
              onChange={handleChange}
              required
            />
          </div>

          <div className="input-group">
            <label>Age</label>
            <input
              type="number"
              name="age"
              placeholder="Age"
              value={formData.age}
              onChange={handleChange}
              required
            />
          </div>

          <div className="input-group">
            <label>Sex</label>
            <select name="sex" value={formData.sex} onChange={handleChange} required>
              <option value="">Sex</option>
              <option value="Male">Male</option>
              <option value="Female">Female</option>
              <option value="Other">Other</option>
            </select>
          </div>
        </div>

        <button type="submit" className="submit-btn">
          BOOK APPOINTMENT
        </button>
      </form>
    </div>
  );
};

export default WithoutAppointmentPage;
