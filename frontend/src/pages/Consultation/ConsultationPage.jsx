import React from 'react';
import { useNavigate } from 'react-router-dom';
import { FiCalendar, FiUserPlus, FiChevronRight } from 'react-icons/fi';

const OPTIONS = [
  {
    key: 'with',
    path: '/consultation/with',
    icon: FiCalendar,
    title: 'With Appointment',
    description: 'Pull up a patient who already has a scheduled slot today.',
  },
  {
    key: 'without',
    path: '/consultation/without',
    icon: FiUserPlus,
    title: 'Without Appointment',
    description: 'Register a walk-in patient and start their consultation now.',
  },
];

const ConsultationPage = () => {
  const navigate = useNavigate();

  const handleKeyDown = (e, path) => {
    if (e.key === 'Enter' || e.key === ' ') {
      e.preventDefault();
      navigate(path);
    }
  };

  return (
    <div className="min-h-screen flex flex-col items-center justify-center p-8">
      <div className="text-center mb-12">
        <h1 className="text-4xl font-bold text-gray-800 tracking-wide">CONSULTATION PANEL</h1>
        <p className="text-gray-600 mt-2">Choose how you'd like to start this consultation</p>
      </div>

      <div className="flex flex-col md:flex-row gap-8">
        {OPTIONS.map(({ key, path, icon: Icon, title, description }) => (
          <div
            key={key}
            role="button"
            tabIndex={0}
            onClick={() => navigate(path)}
            onKeyDown={(e) => handleKeyDown(e, path)}
            aria-label={title}
            className="group w-64 h-80 bg-white/30 hover:bg-white/50 backdrop-blur-md rounded-3xl shadow-lg hover:shadow-2xl border border-white/40 flex flex-col items-center justify-center gap-4 cursor-pointer transition-all duration-300 transform hover:scale-105 focus:outline-none focus:ring-2 focus:ring-purple-400 focus:ring-offset-2 px-6 text-center"
          >
            <div className="w-16 h-16 rounded-2xl bg-purple-500/15 text-purple-700 flex items-center justify-center group-hover:bg-purple-500/25 transition-colors">
              <Icon size={28} />
            </div>
            <span className="text-xl font-semibold text-gray-800">{title}</span>
            <p className="text-sm text-gray-600 leading-relaxed">{description}</p>
            <span className="flex items-center gap-1 text-sm font-medium text-purple-700 opacity-0 group-hover:opacity-100 transition-opacity">
              Continue <FiChevronRight size={16} />
            </span>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ConsultationPage;