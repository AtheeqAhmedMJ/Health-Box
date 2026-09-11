import React, { useEffect, useState, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import { appointmentsAPI } from '../../services/api';
import { useApp } from '../../context/AppContext';
import { APPOINTMENT_STATUS } from '../../constants';
import { SkeletonCard } from '../../components/Skeleton/Skeleton';
import {
  FiArrowRight,
  FiArrowLeft,
  FiCalendar,
  FiSearch,
  FiPhone,
  FiRefreshCw,
  FiUsers,
} from 'react-icons/fi';

const WithAppointment = () => {
  const [appointments, setAppointments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [search, setSearch] = useState('');
  const navigate = useNavigate();
  const { notify } = useApp();

  const fetchAppointments = async () => {
    setLoading(true);
    setError('');
    try {
      // NOTE: the axios response interceptor (src/middleware/apiInterceptors.js)
      // already unwraps `response.data`, so the resolved value here IS the
      // array of appointments — not an axios response object.
      const data = await appointmentsAPI.getWithDetails();
      const scheduled = (Array.isArray(data) ? data : []).filter(
        (a) => a.status === APPOINTMENT_STATUS.SCHEDULED
      );
      scheduled.sort((a, b) => new Date(a.date) - new Date(b.date));
      setAppointments(scheduled);
    } catch (err) {
      setAppointments([]);
      setError('Could not load today\'s appointments.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchAppointments();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const filtered = useMemo(() => {
    const q = search.trim().toLowerCase();
    if (!q) return appointments;
    return appointments.filter(
      (a) =>
        (a.name || '').toLowerCase().includes(q) ||
        (a.phno || '').includes(search.trim())
    );
  }, [appointments, search]);

  const handleSelect = (a) => {
    if (!a.phno) {
      notify.error('This appointment is missing a patient phone number.');
      return;
    }
    navigate(`/prescriptions?phno=${a.phno}&appointmentId=${a.appointmentId}`);
  };

  return (
    <div className="min-h-screen p-8 max-w-3xl mx-auto">
      <button
        onClick={() => navigate('/consultation')}
        className="flex items-center gap-1 text-sm font-medium text-gray-600 hover:text-purple-700 transition-colors mb-4"
      >
        <FiArrowLeft size={16} /> Back
      </button>

      <div className="flex items-center justify-between mb-6">
        <div>
          <h1 className="text-3xl font-bold text-gray-800">WITH APPOINTMENT</h1>
          <p className="text-gray-600 text-sm mt-1">
            {loading ? 'Loading scheduled patients…' : `${appointments.length} scheduled today`}
          </p>
        </div>
        <button
          onClick={fetchAppointments}
          disabled={loading}
          aria-label="Refresh appointments"
          className="p-3 rounded-full bg-white/40 hover:bg-white/60 text-purple-700 border border-white/40 transition-all disabled:opacity-50"
        >
          <FiRefreshCw size={18} className={loading ? 'animate-spin' : ''} />
        </button>
      </div>

      <div className="relative mb-8">
        <FiSearch className="absolute left-4 top-1/2 -translate-y-1/2 text-gray-400" size={18} />
        <input
          type="text"
          placeholder="Search by name or phone..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          className="w-full pl-11 pr-4 py-3 rounded-lg border border-white/40 bg-white/50 focus:outline-none focus:ring-2 focus:ring-purple-400"
        />
      </div>

      {error && (
        <div className="mb-6 p-4 bg-red-50/80 border border-red-200 rounded-lg text-sm text-red-700 flex items-center justify-between">
          <span>{error}</span>
          <button onClick={fetchAppointments} className="font-semibold underline">
            Retry
          </button>
        </div>
      )}

      <div className="bg-white/40 backdrop-blur-md rounded-2xl shadow-lg p-6 border border-white/20">
        {loading ? (
          <SkeletonCard count={3} />
        ) : filtered.length === 0 ? (
          <div className="text-center py-12">
            <FiUsers className="mx-auto mb-3 text-gray-400" size={32} />
            <p className="text-gray-600 font-medium">
              {search ? 'No matching appointments found.' : 'No scheduled appointments today.'}
            </p>
          </div>
        ) : (
          filtered.map((a, i) => (
            <div
              key={a.appointmentId}
              role="button"
              tabIndex={0}
              onClick={() => handleSelect(a)}
              onKeyDown={(e) => e.key === 'Enter' && handleSelect(a)}
              className="flex items-center justify-between bg-white rounded-xl px-4 py-3 mb-3 last:mb-0 shadow-sm cursor-pointer hover:-translate-y-0.5 hover:shadow-md transition-all focus:outline-none focus:ring-2 focus:ring-purple-400"
            >
              <div className="flex items-center gap-3">
                <div className="w-8 h-8 rounded-full bg-purple-200 text-purple-700 font-semibold flex items-center justify-center text-sm shrink-0">
                  {i + 1}
                </div>
                <div>
                  <p className="font-medium text-gray-800">{a.name || 'Unnamed patient'}</p>
                  <div className="flex items-center gap-3 text-xs text-gray-500 mt-0.5">
                    <span className="flex items-center gap-1">
                      <FiCalendar size={12} /> {a.date}
                    </span>
                    {a.phno && (
                      <span className="flex items-center gap-1">
                        <FiPhone size={12} /> {a.phno}
                      </span>
                    )}
                  </div>
                </div>
              </div>
              <FiArrowRight className="text-purple-500 shrink-0" />
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default WithAppointment;