import React from 'react';
import { FiCalendar } from 'react-icons/fi';

const SchedulingPage = () => {
  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-50/50 via-pink-50/30 to-white p-4 md:p-8">
      <div className="max-w-7xl mx-auto">
        <h1 className="text-4xl font-bold text-gray-900 flex items-center gap-3 mb-8">
          <FiCalendar className="text-purple-600" />
          Scheduling
        </h1>
        <div className="bg-white/40 backdrop-blur-md rounded-xl p-6 shadow-lg border border-white/20">
          <p className="text-gray-600">Scheduling module is being implemented</p>
        </div>
      </div>
    </div>
  );
};

export default SchedulingPage;
