import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { appointmentsAPI } from '../../services/api';
import { useApp } from '../../context/AppContext';
import { validatePhoneNumber, formatDate } from '../../utils';
import { SUCCESS_MESSAGES } from '../../constants';
import { FiArrowLeft, FiUser, FiPhone, FiChevronDown, FiUserPlus, FiCheck } from 'react-icons/fi';

const todayStr = () => new Date().toISOString().slice(0, 10);

const initialForm = { name: '', phno: '', age: '', sex: '' };

const STEPS = [
  { title: 'Patient details', text: 'Name, phone, age and sex are captured for the visit.' },
  { title: 'Instant registration', text: 'The patient is booked the moment you submit — no queue.' },
  { title: 'Straight to prescriptions', text: "You're taken to their prescription page automatically." },
];

const WithoutAppointment = () => {
  const [formData, setFormData] = useState(initialForm);
  const [fieldErrors, setFieldErrors] = useState({});
  const [touched, setTouched] = useState({});
  const [error, setError] = useState('');
  const [saving, setSaving] = useState(false);
  const navigate = useNavigate();
  const { notify } = useApp();

  const validate = (data) => {
    const errors = {};
    if (!data.name.trim()) errors.name = 'Patient name is required';

    if (!data.phno.trim()) {
      errors.phno = 'Phone number is required';
    } else if (!validatePhoneNumber(data.phno)) {
      errors.phno = 'Enter a valid 10-digit phone number';
    }

    const ageNum = Number(data.age);
    if (!data.age) {
      errors.age = 'Age is required';
    } else if (!Number.isFinite(ageNum) || ageNum <= 0 || ageNum > 120) {
      errors.age = 'Enter a valid age';
    }

    if (!data.sex) errors.sex = 'Select sex';

    return errors;
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    const nextValue = name === 'phno' ? value.replace(/\D/g, '').slice(0, 10) : value;
    const next = { ...formData, [name]: nextValue };
    setFormData(next);
    if (touched[name]) {
      setFieldErrors(validate(next));
    }
  };

  const handleBlur = (e) => {
    const { name } = e.target;
    setTouched((prev) => ({ ...prev, [name]: true }));
    setFieldErrors(validate(formData));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    const errors = validate(formData);
    setFieldErrors(errors);
    setTouched({ name: true, phno: true, age: true, sex: true });
    if (Object.keys(errors).length > 0) {
      return;
    }

    setSaving(true);
    try {
      await appointmentsAPI.create({
        patientPhno: formData.phno,
        patientName: formData.name.trim(),
        patientAge: Number(formData.age),
        patientSex: formData.sex,
        date: todayStr(),
      });
      notify.success(SUCCESS_MESSAGES.APPOINTMENT_CREATED);
      navigate(`/prescriptions?phno=${formData.phno}`);
    } catch (err) {
      setError(err.response?.data?.error || err.response?.data?.message || 'Could not register walk-in patient');
    } finally {
      setSaving(false);
    }
  };

  const showError = (field) => touched[field] && fieldErrors[field];

  const inputClass = (field) =>
    `w-full pl-11 pr-4 py-3.5 rounded-xl border bg-white text-gray-800 placeholder:text-gray-400 focus:outline-none focus:ring-2 focus:ring-offset-1 transition-all ${
      showError(field)
        ? 'border-red-300 focus:ring-red-400'
        : 'border-gray-200 focus:ring-purple-400 focus:border-purple-300'
    }`;

  return (
    <div className="min-h-screen flex flex-col items-center justify-center p-6 md:p-10">
      <div className="w-full max-w-4xl">
        <button
          onClick={() => navigate('/consultation')}
          className="flex items-center gap-1 text-sm font-medium text-gray-700 hover:text-purple-800 transition-colors mb-5"
        >
          <FiArrowLeft size={16} /> Back
        </button>

        {error && (
          <div className="mb-5 p-3 bg-red-50/90 border border-red-200 rounded-lg text-sm text-red-700">
            {error}
          </div>
        )}

        <div className="grid grid-cols-1 md:grid-cols-5 rounded-3xl shadow-2xl overflow-hidden border border-white/40">
          {/* Info panel */}
          <div className="md:col-span-2 relative bg-gradient-to-br from-[#4a3358a2] via-[#5c3f6ea3] to-[#7a4f8c85] backdrop-blur-xl border-r border-white/10 p-8 md:p-10 text-white overflow-hidden">
            <div className="absolute -top-10 -right-10 w-40 h-40 rounded-full bg-white/10 blur-2xl" />
            <div className="absolute bottom-0 left-0 w-56 h-56 rounded-full bg-purple-300/10 blur-3xl" />

            <div className="relative">
              <div className="w-12 h-12 rounded-2xl bg-white/15 flex items-center justify-center mb-6">
                <FiUserPlus size={22} />
              </div>
              <p className="text-xs font-semibold tracking-widest text-purple-200 uppercase mb-2">
                {formatDate(new Date(), 'MMM DD, YYYY')}
              </p>
              <h1 className="text-3xl font-bold leading-tight">Walk-in Registration</h1>
              <p className="text-purple-100/90 text-sm mt-3 leading-relaxed">
                No appointment needed — get this patient into the system and on their way to the doctor in seconds.
              </p>

              <div className="mt-9 space-y-5">
                {STEPS.map((step, i) => (
                  <div key={step.title} className="flex gap-3">
                    <div className="w-6 h-6 rounded-full bg-white/15 flex items-center justify-center shrink-0 mt-0.5">
                      <FiCheck size={13} />
                    </div>
                    <div>
                      <p className="text-sm font-semibold">{step.title}</p>
                      <p className="text-xs text-purple-100/75 mt-0.5 leading-relaxed">{step.text}</p>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          </div>

          {/* Form panel */}
          <div className="md:col-span-3 bg-white/50 backdrop-blur-xl p-8 md:p-10">
            <form onSubmit={handleSubmit} noValidate className="flex flex-col gap-5">
              <div>
                <label className="block text-sm font-semibold text-gray-700 mb-1.5">
                  Patient Name <span className="text-red-500">*</span>
                </label>
                <div className="relative">
                  <FiUser className="absolute left-4 top-1/2 -translate-y-1/2 text-gray-400" size={17} />
                  <input
                    type="text"
                    name="name"
                    value={formData.name}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    className={inputClass('name')}
                    placeholder="Full name"
                  />
                </div>
                {showError('name') && <p className="text-red-600 text-xs mt-1">{fieldErrors.name}</p>}
              </div>

              <div>
                <label className="block text-sm font-semibold text-gray-700 mb-1.5">
                  Phone Number <span className="text-red-500">*</span>
                </label>
                <div className="relative">
                  <FiPhone className="absolute left-4 top-1/2 -translate-y-1/2 text-gray-400" size={17} />
                  <input
                    type="tel"
                    name="phno"
                    inputMode="numeric"
                    value={formData.phno}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    maxLength={10}
                    className={inputClass('phno')}
                    placeholder="9876543210"
                  />
                </div>
                {showError('phno') && <p className="text-red-600 text-xs mt-1">{fieldErrors.phno}</p>}
              </div>

              <div className="flex flex-col sm:flex-row gap-4">
                <div className="flex-1">
                  <label className="block text-sm font-semibold text-gray-700 mb-1.5">
                    Age <span className="text-red-500">*</span>
                  </label>
                  <input
                    type="number"
                    name="age"
                    min={0}
                    max={120}
                    value={formData.age}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    className={`w-full px-4 py-3.5 rounded-xl border bg-white text-gray-800 placeholder:text-gray-400 focus:outline-none focus:ring-2 focus:ring-offset-1 transition-all ${
                      showError('age')
                        ? 'border-red-300 focus:ring-red-400'
                        : 'border-gray-200 focus:ring-purple-400 focus:border-purple-300'
                    }`}
                    placeholder="Age"
                  />
                  {showError('age') && <p className="text-red-600 text-xs mt-1">{fieldErrors.age}</p>}
                </div>
                <div className="flex-1">
                  <label className="block text-sm font-semibold text-gray-700 mb-1.5">
                    Sex <span className="text-red-500">*</span>
                  </label>
                  <div className="relative">
                    <select
                      name="sex"
                      value={formData.sex}
                      onChange={handleChange}
                      onBlur={handleBlur}
                      className={`w-full appearance-none px-4 pr-10 py-3.5 rounded-xl border bg-white text-gray-800 focus:outline-none focus:ring-2 focus:ring-offset-1 transition-all ${
                        showError('sex')
                          ? 'border-red-300 focus:ring-red-400'
                          : 'border-gray-200 focus:ring-purple-400 focus:border-purple-300'
                      } ${!formData.sex ? 'text-gray-400' : ''}`}
                    >
                      <option value="" disabled>
                        Select
                      </option>
                      <option value="Male">Male</option>
                      <option value="Female">Female</option>
                      <option value="Other">Other</option>
                    </select>
                    <FiChevronDown
                      className="absolute right-3.5 top-1/2 -translate-y-1/2 text-gray-400 pointer-events-none"
                      size={16}
                    />
                  </div>
                  {showError('sex') && <p className="text-red-600 text-xs mt-1">{fieldErrors.sex}</p>}
                </div>
              </div>

              <button
                type="submit"
                disabled={saving}
                className="mt-3 py-3.5 rounded-xl font-bold text-white bg-gradient-to-r from-[#7a4f8c] to-[#9a5fae] hover:from-[#6b4577] hover:to-[#8a4f9c] shadow-lg shadow-purple-900/20 transition-all disabled:opacity-50 flex items-center justify-center gap-2"
              >
                {saving && <div className="animate-spin rounded-full h-4 w-4 border-b-2 border-current" />}
                {saving ? 'Booking...' : 'BOOK APPOINTMENT'}
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default WithoutAppointment;