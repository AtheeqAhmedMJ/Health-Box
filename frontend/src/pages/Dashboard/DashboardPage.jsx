import React, { useState, useEffect } from 'react';
import { dashboardAPI } from '../../services/api';
import StatCard from '../../components/StatCard/StatCard';
import ChartComponent from '../../components/Chart/ChartComponent';
import {
  FiCalendar,
  FiUsers,
  FiTrendingUp,
  FiCheckCircle,
  FiDollarSign,
} from 'react-icons/fi';

const DashboardPage = () => {
  const [analytics, setAnalytics] = useState({
    todayAppointments: 0,
    monthlyAppointments: 0,
    totalPatients: 0,
    recurringPatientPercentage: 0,
    graphData: [],
  });

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchDashboardData = async () => {
      try {
        setLoading(true);

        const userRole = JSON.parse(
          localStorage.getItem('user')
        )?.role;

        if (userRole === 'ADMIN') {
          const data = await dashboardAPI.getAdminSummary();

          setAnalytics({
            todayAppointments: data.todaysAppointments || 0,
            monthlyAppointments: data.monthlyAppointments || 0,
            totalPatients: data.totalPatients || 0,
            recurringPatientPercentage:
              data.recurringPatientsPercentage || 0,
            graphData: Object.entries(data.graphData || {}).map(
              ([date, count]) => ({
                date,
                appointments: count,
              })
            ),
          });
        } else {
          const data = await dashboardAPI.getPatientSummary();

          setAnalytics((prev) => ({
            ...prev,
            graphData: Object.entries(data.graphData || {}).map(
              ([date, count]) => ({
                date,
                value: count,
              })
            ),
          }));
        }
      } catch (err) {
        console.error('Failed to fetch dashboard data:', err);
        setError('Failed to load dashboard data');
      } finally {
        setLoading(false);
      }
    };

    fetchDashboardData();
  }, []);

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-[calc(100vh-80px)]">
        <div className="text-center">
          <div className="animate-spin rounded-full h-16 w-16 border-2 border-white/20 border-t-[#1f1f2e] mx-auto mb-4" />

          <p className="text-gray-700 font-medium">
            Loading dashboard...
          </p>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen p-4 md:p-8">
      <div className="max-w-7xl mx-auto">

        <div className="mb-8">
          <h1 className="text-4xl font-bold text-gray-900 mb-2">
            Dashboard
          </h1>

          <p className="text-gray-600">
            Welcome back! Here's your healthcare overview
          </p>
        </div>

        {error && (
          <div className="mb-6 p-4 bg-red-500/10 backdrop-blur-md border border-red-400/30 rounded-xl text-red-700">
            {error}
          </div>
        )}

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
          <StatCard
            title="Today's Appointments"
            value={analytics.todayAppointments}
            icon={FiCalendar}
            color="bg-[rgba(31,31,46,0.52)]"
            trend="+12% from yesterday"
          />

          <StatCard
            title="Monthly Appointments"
            value={analytics.monthlyAppointments}
            icon={FiTrendingUp}
            color="bg-[rgba(31,31,46,0.52)]"
            trend="+5% from last month"
          />

          <StatCard
            title="Total Patients"
            value={analytics.totalPatients}
            icon={FiUsers}
            color="bg-[rgba(31,31,46,0.52)]"
            trend="+8 new patients"
          />

          <StatCard
            title="Recurring Patients"
            value={`${analytics.recurringPatientPercentage}%`}
            icon={FiCheckCircle}
            color="bg-[rgba(31,31,46,0.52)]"
            trend="of total patient base"
          />
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
          <div className="lg:col-span-2 bg-[rgba(225,210,232,0.72)] backdrop-blur-md rounded-xl p-6 shadow-lg border border-white/20">
            <h2 className="text-xl font-bold text-gray-900 mb-6">
              Appointment Trends
            </h2>

            <ChartComponent data={analytics.graphData} />
          </div>

          <div className="bg-purple-500/10 backdrop-blur-md rounded-xl p-6 shadow-lg border border-purple-300/30">
            <div className="flex items-center gap-3 mb-4">
              <div className="p-3 bg-purple-500/20 rounded-lg">
                <FiDollarSign
                  size={24}
                  className="text-purple-700"
                />
              </div>

              <div>
                <p className="text-sm text-gray-600">
                  Revenue
                </p>

                <h3 className="text-2xl font-bold text-gray-900">
                  ₹2,45,000
                </h3>
              </div>
            </div>

            <p className="text-sm text-gray-600 mb-4">
              This month
            </p>

            <div className="space-y-2 text-sm">
              <div className="flex justify-between">
                <span className="text-gray-600">
                  Consultations
                </span>

                <span className="font-semibold text-gray-900">
                  ₹1,80,000
                </span>
              </div>

              <div className="flex justify-between">
                <span className="text-gray-600">
                  Pharmacy
                </span>

                <span className="font-semibold text-gray-900">
                  ₹45,000
                </span>
              </div>

              <div className="flex justify-between">
                <span className="text-gray-600">
                  Procedures
                </span>

                <span className="font-semibold text-gray-900">
                  ₹20,000
                </span>
              </div>
            </div>
          </div>
        </div>

      </div>
    </div>
  );
};

export default DashboardPage;