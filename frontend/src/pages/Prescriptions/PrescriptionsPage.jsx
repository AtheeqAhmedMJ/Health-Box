import React, { useState, useEffect } from 'react';
import { prescriptionsAPI } from '../../services/api';
import { FiFileText, FiSearch } from 'react-icons/fi';

const PrescriptionsPage = () => {
  const [prescriptions, setPrescriptions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    fetchPrescriptions();
  }, []);

  const fetchPrescriptions = async () => {
    try {
      const data = await prescriptionsAPI.getAll();
      setPrescriptions(data);
    } catch (err) {
      console.error('Failed to fetch prescriptions:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-50/50 via-pink-50/30 to-white p-4 md:p-8">
      <div className="max-w-7xl mx-auto">
        <div className="mb-8">
          <h1 className="flex items-center gap-3 text-4xl font-bold text-gray-900">
            <FiFileText className="text-purple-600" />
            Prescriptions
          </h1>

          <p className="mt-1 text-gray-600">
            View and manage patient prescriptions
          </p>
        </div>

        <div className="relative mb-6">
          <FiSearch
            className="absolute left-4 top-1/2 -translate-y-1/2 text-gray-400"
            size={20}
          />

          <input
            type="text"
            placeholder="Search prescriptions..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="w-full rounded-lg border border-white/20 bg-white/40 py-3 pl-12 pr-4 focus:outline-none focus:ring-2 focus:ring-purple-500"
          />
        </div>

        {loading ? (
          <div className="py-12 text-center">
            <div className="mx-auto mb-3 h-12 w-12 animate-spin rounded-full border-b-2 border-purple-500" />
          </div>
        ) : (
          <div className="space-y-4">
            {prescriptions.length === 0 ? (
              <div className="rounded-xl border border-white/20 bg-white/40 py-12 text-center">
                <p className="text-gray-600">
                  No prescriptions found
                </p>
              </div>
            ) : (
              prescriptions.map((rx) => (
                <div
                  key={rx.id}
                  className="rounded-xl border border-white/20 bg-white/40 p-6 shadow-lg backdrop-blur-md transition-shadow hover:shadow-xl"
                >
                  <div className="flex items-start justify-between">
                    <div>
                      <h3 className="text-lg font-bold text-gray-900">
                        {rx.medicationName || 'N/A'}
                      </h3>

                      <p className="mt-1 text-sm text-gray-600">
                        {rx.dosage || 'N/A'} - {rx.frequency || 'N/A'}
                      </p>
                    </div>

                    <span className="rounded-full bg-green-100 px-3 py-1 text-sm font-semibold text-green-800">
                      Active
                    </span>
                  </div>
                </div>
              ))
            )}
          </div>
        )}
      </div>
    </div>
  );
};

export default PrescriptionsPage;