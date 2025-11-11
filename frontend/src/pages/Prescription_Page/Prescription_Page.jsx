import React, { useState } from 'react'; 
import './Prescription_Page.css'; 

const Prescription_Page = () => {
  const [medicationData, setMedicationData] = useState({
    medicationType: 'Tab',
    medication: '',
    dosage: '',
    quantity: '',
    duration: '',
    food: 'After Food',
  });

  const [medications, setMedications] = useState([]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setMedicationData((prev) => ({ ...prev, [name]: value }));
  };

  const addMedication = () => {
    const { medication, dosage, quantity, duration } = medicationData;
    if (!medication || !dosage || !quantity || !duration) return;

    setMedications((prev) => [...prev, medicationData]);

    setMedicationData({
      medicationType: 'Tab',
      medication: '',
      dosage: '',
      quantity: '',
      duration: '',
      food: 'After Food',
    });
  };

  return (
   <>
    <h1 className='Consultation'>PRESCRIPTION PANEL</h1>
    <div className="medication-form-container">
      <div className="medication-form">
        <div className="medication-box">
          <label>Type:</label>
          <select
            name="medicationType"
            value={medicationData.medicationType}
            onChange={handleChange}
            required
          >
            <option value="Tab">Tab</option>
            <option value="Cap">Cap</option>
            <option value="Syrup">Syrup</option>
            <option value="Oint">Oint</option>
            <option value="Powder">Powder</option>
            <option value="Injc">Injc</option>
            <option value="Others">Others</option>
          </select>
        </div>

        <div className="medication-box">
          <label>Medication:</label>
          <input
            type="text"
            name="medication"
            value={medicationData.medication}
            onChange={handleChange}
            required
          />
        </div>

        <div className="medication-box">
          <label>Dosage:</label>
          <input
            type="text"
            name="dosage"
            value={medicationData.dosage}
            onChange={handleChange}
            required
          />
        </div>

        <div className="medication-box">
          <label>Quantity:</label>
          <input
            type="text"
            name="quantity"
            value={medicationData.quantity}
            onChange={handleChange}
            required
          />
        </div>

        <div className="medication-box">
          <label>Duration:</label>
          <input
            type="text"
            name="duration"
            value={medicationData.duration}
            onChange={handleChange}
            required
          />
        </div>

        <div className="medication-box">
          <label>Food:</label>
          <select
            name="food"
            value={medicationData.food}
            onChange={handleChange}
            required
          >
            <option value="After Food">After Food</option>
            <option value="With Food">With Food</option>
            <option value="Before Food">Before Food</option>
          </select>
        </div>

        <button type="button" className="styled-button" onClick={addMedication}>
          Add Medication
        </button>
      </div>

      <div className="medications-list">
        <h3>Medications Added</h3>
        {medications.length === 0 ? (
          <p>No medications added yet.</p>
        ) : (
          <ul>
            {medications.map((med, idx) => (
              <li key={idx}>
                <strong>{med.medicationType}</strong> {med.medication} – {med.dosage} –{' '}
                {med.quantity} – {med.duration} – <em>{med.food}</em>
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
   </>
  );
};

export default Prescription_Page;
