import React, { useState, useEffect, useContext } from 'react';
import { FiCreditCard, FiRefreshCw, FiCheck, FiX, FiSearch, FiFilter } from 'react-icons/fi';
import { AppContext } from '../../context/AppContext';
import axios from 'axios';

const PaymentsPage = () => {
  const { user } = useContext(AppContext);
  const [payments, setPayments] = useState([]);
  const [billings, setBillings] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [searchTerm, setSearchTerm] = useState('');
  const [filterStatus, setFilterStatus] = useState('all');
  const [selectedBilling, setSelectedBilling] = useState(null);
  const [showPaymentModal, setShowPaymentModal] = useState(false);
  const [paymentMethod, setPaymentMethod] = useState('razorpay');
  const [amount, setAmount] = useState('');
  const [changeGiven, setChangeGiven] = useState('');
  const [isProcessing, setIsProcessing] = useState(false);

  const API_URL = import.meta.env.VITE_API_URL;
  const token = localStorage.getItem('authToken');

  // Fetch payments and billings
  useEffect(() => {
    fetchPayments();
    fetchPendingBillings();
  }, [filterStatus]);

  const fetchPayments = async () => {
    setLoading(true);
    try {
      const response = await axios.get(`${API_URL}/payments`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      setPayments(Array.isArray(response.data) ? response.data : []);
    } catch (err) {
      console.error('Error fetching payments:', err);
    } finally {
      setLoading(false);
    }
  };

  const fetchPendingBillings = async () => {
    try {
      const response = await axios.get(`${API_URL}/billing?status=PENDING`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      setBillings(Array.isArray(response.data) ? response.data : []);
    } catch (err) {
      console.error('Error fetching billings:', err);
    }
  };

  const handleInitiatePayment = async (billingId, billingAmount) => {
    setIsProcessing(true);
    setError('');
    setSuccess('');

    try {
      if (paymentMethod === 'razorpay') {
        // Call Razorpay checkout
        const response = await axios.post(
          `${API_URL}/payments/checkout`,
          {
            billingId: billingId,
            amount: billingAmount
          },
          { headers: { Authorization: `Bearer ${token}` } }
        );

        const { orderId, amount: rzpAmount, currency } = response.data;

        // Razorpay options
        const options = {
          key: import.meta.env.VITE_RAZORPAY_KEY_ID || 'rzp_test_default',
          amount: rzpAmount,
          currency: currency,
          name: 'HealthBox',
          description: `Payment for Billing #${billingId}`,
          order_id: orderId,
          handler: async (paymentResponse) => {
            try {
              // Verify payment
              const verifyResponse = await axios.post(
                `${API_URL}/payments/verify`,
                {
                  orderId: orderId,
                  paymentId: paymentResponse.razorpay_payment_id,
                  signature: paymentResponse.razorpay_signature
                },
                { headers: { Authorization: `Bearer ${token}` } }
              );

              setSuccess(`Payment of ${(rzpAmount / 100).toFixed(2)} completed successfully!`);
              fetchPayments();
              fetchPendingBillings();
              setShowPaymentModal(false);
              setTimeout(() => setSuccess(''), 5000);
            } catch (verifyErr) {
              setError('Payment verification failed. Please contact support.');
              console.error('Payment verification error:', verifyErr);
            }
          },
          prefill: {
            name: user?.username || 'Customer',
            email: user?.email || ''
          },
          theme: {
            color: '#9333ea'
          }
        };

        // Load Razorpay script and open checkout
        const script = document.createElement('script');
        script.src = 'https://checkout.razorpay.com/v1/checkout.js';
        script.async = true;
        script.onload = () => {
          const rzp = new window.Razorpay(options);
          rzp.open();
        };
        document.head.appendChild(script);
      } else if (paymentMethod === 'cash') {
        // Cash payment
        const response = await axios.post(
          `${API_URL}/payments/cash`,
          {
            billingId: billingId,
            amountReceived: amount,
            changeGiven: changeGiven
          },
          { headers: { Authorization: `Bearer ${token}` } }
        );

        setSuccess('Cash payment recorded successfully!');
        fetchPayments();
        fetchPendingBillings();
        setShowPaymentModal(false);
        setAmount('');
        setChangeGiven('');
        setTimeout(() => setSuccess(''), 5000);
      }
    } catch (err) {
      setError(err.response?.data?.message || 'Payment failed. Please try again.');
      console.error('Payment error:', err);
    } finally {
      setIsProcessing(false);
    }
  };

  const filteredPayments = payments.filter(payment =>
    payment.id.toString().includes(searchTerm) ||
    payment.billingId?.toString().includes(searchTerm) ||
    payment.orderId?.includes(searchTerm)
  );

  const stats = {
    total: payments.length,
    pending: billings.length,
    totalAmount: payments.reduce((sum, p) => sum + (p.amountReceived || 0), 0)
  };

  const getStatusColor = (status) => {
    switch (status) {
      case 'SUCCESS':
      case 'COMPLETED': return 'bg-green-100 text-green-800';
      case 'PENDING': return 'bg-yellow-100 text-yellow-800';
      case 'FAILED': return 'bg-red-100 text-red-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  };

  const formatCurrency = (amount) => {
    return `₹${(amount / 100).toFixed(2)}`;
  };

  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleDateString('en-IN', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-50/50 via-pink-50/30 to-white p-4 md:p-8">
      <div className="max-w-7xl mx-auto">
        {/* Header */}
        <div className="flex items-center justify-between mb-8">
          <h1 className="text-4xl font-bold text-gray-900 flex items-center gap-3">
            <FiCreditCard className="text-purple-600" size={36} />
            Payments
          </h1>
          <button
            onClick={fetchPayments}
            disabled={loading}
            className="flex items-center gap-2 bg-purple-600 hover:bg-purple-700 text-white px-4 py-2 rounded-lg transition-colors disabled:opacity-50"
          >
            <FiRefreshCw className={loading ? 'animate-spin' : ''} size={18} />
            Refresh
          </button>
        </div>

        {error && (
          <div className="bg-red-50 border border-red-200 rounded-lg p-4 mb-6 text-red-800 flex justify-between items-center">
            <span>{error}</span>
            <button onClick={() => setError('')} className="text-red-600 hover:text-red-800">
              <FiX size={20} />
            </button>
          </div>
        )}

        {success && (
          <div className="bg-green-50 border border-green-200 rounded-lg p-4 mb-6 text-green-800 flex justify-between items-center">
            <span className="flex items-center gap-2">
              <FiCheck size={20} />
              {success}
            </span>
            <button onClick={() => setSuccess('')} className="text-green-600 hover:text-green-800">
              <FiX size={20} />
            </button>
          </div>
        )}

        {/* Stats */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-8">
          <div className="bg-white/40 backdrop-blur-md rounded-xl p-6 shadow-lg border border-white/20">
            <p className="text-gray-600 text-sm font-medium mb-2">Total Payments</p>
            <p className="text-3xl font-bold text-gray-900">{stats.total}</p>
          </div>
          <div className="bg-white/40 backdrop-blur-md rounded-xl p-6 shadow-lg border border-white/20">
            <p className="text-gray-600 text-sm font-medium mb-2">Pending Billings</p>
            <p className="text-3xl font-bold text-yellow-600">{stats.pending}</p>
          </div>
          <div className="bg-white/40 backdrop-blur-md rounded-xl p-6 shadow-lg border border-white/20">
            <p className="text-gray-600 text-sm font-medium mb-2">Total Amount</p>
            <p className="text-2xl font-bold text-purple-600">{formatCurrency(stats.totalAmount)}</p>
          </div>
        </div>

        {/* Tabs */}
        <div className="flex gap-4 mb-6">
          <button
            onClick={() => setFilterStatus('all')}
            className={`px-4 py-2 rounded-lg font-medium transition-all ${
              filterStatus === 'all'
                ? 'bg-purple-600 text-white'
                : 'bg-white/40 text-gray-900 hover:bg-white/60'
            }`}
          >
            All Payments
          </button>
          <button
            onClick={() => setFilterStatus('pending')}
            className={`px-4 py-2 rounded-lg font-medium transition-all ${
              filterStatus === 'pending'
                ? 'bg-purple-600 text-white'
                : 'bg-white/40 text-gray-900 hover:bg-white/60'
            }`}
          >
            Pending Billings ({billings.length})
          </button>
        </div>

        {filterStatus === 'pending' && billings.length > 0 ? (
          // Pending Billings
          <div className="bg-white/40 backdrop-blur-md rounded-xl shadow-lg border border-white/20 overflow-hidden mb-8">
            <div className="p-6">
              <h2 className="text-xl font-bold text-gray-900 mb-4">Pending Payments</h2>
              <div className="space-y-3">
                {billings.map((billing) => (
                  <div
                    key={billing.id}
                    className="flex items-center justify-between p-4 bg-white/50 rounded-lg border border-white/20"
                  >
                    <div>
                      <p className="font-bold text-gray-900">Billing #{billing.id}</p>
                      <p className="text-sm text-gray-600">Amount: {formatCurrency(billing.totalAmount)}</p>
                    </div>
                    <button
                      onClick={() => {
                        setSelectedBilling(billing);
                        setAmount((billing.totalAmount / 100).toString());
                        setShowPaymentModal(true);
                      }}
                      className="bg-purple-600 hover:bg-purple-700 text-white px-6 py-2 rounded-lg font-medium transition-colors"
                    >
                      Pay Now
                    </button>
                  </div>
                ))}
              </div>
            </div>
          </div>
        ) : null}

        {filterStatus === 'all' && (
          // Payment History
          <>
            <div className="flex flex-col md:flex-row gap-4 mb-6">
              <div className="flex-1 relative">
                <FiSearch className="absolute left-4 top-3 text-gray-400" size={20} />
                <input
                  type="text"
                  placeholder="Search by ID or order..."
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  className="w-full pl-12 pr-4 py-2 bg-white/40 backdrop-blur-md border border-white/20 rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-500"
                />
              </div>
            </div>

            <div className="bg-white/40 backdrop-blur-md rounded-xl shadow-lg border border-white/20 overflow-hidden">
              {loading ? (
                <div className="p-8 text-center">
                  <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-purple-600 mx-auto"></div>
                  <p className="text-gray-600 mt-4">Loading payments...</p>
                </div>
              ) : filteredPayments.length === 0 ? (
                <div className="p-8 text-center text-gray-600">
                  No payments found
                </div>
              ) : (
                <div className="overflow-x-auto">
                  <table className="w-full">
                    <thead className="border-b border-white/20 bg-white/20">
                      <tr>
                        <th className="px-6 py-4 text-left text-sm font-semibold text-gray-900">Payment ID</th>
                        <th className="px-6 py-4 text-left text-sm font-semibold text-gray-900">Order ID</th>
                        <th className="px-6 py-4 text-left text-sm font-semibold text-gray-900">Amount</th>
                        <th className="px-6 py-4 text-left text-sm font-semibold text-gray-900">Status</th>
                        <th className="px-6 py-4 text-left text-sm font-semibold text-gray-900">Method</th>
                        <th className="px-6 py-4 text-left text-sm font-semibold text-gray-900">Date</th>
                      </tr>
                    </thead>
                    <tbody className="divide-y divide-white/20">
                      {filteredPayments.map((payment) => (
                        <tr key={payment.id} className="hover:bg-white/30 transition-colors">
                          <td className="px-6 py-4 text-sm font-medium text-gray-900">#{payment.id}</td>
                          <td className="px-6 py-4 text-sm text-gray-700">{payment.orderId || 'N/A'}</td>
                          <td className="px-6 py-4 text-sm font-semibold text-gray-900">
                            {formatCurrency(payment.amountReceived)}
                          </td>
                          <td className="px-6 py-4 text-sm">
                            <span className={`px-3 py-1 rounded-full text-xs font-medium ${getStatusColor(payment.status)}`}>
                              {payment.status || 'COMPLETED'}
                            </span>
                          </td>
                          <td className="px-6 py-4 text-sm text-gray-700 capitalize">
                            {payment.mode || 'Online'}
                          </td>
                          <td className="px-6 py-4 text-sm text-gray-600">
                            {formatDate(payment.createdAt)}
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              )}
            </div>
          </>
        )}

        {/* Payment Modal */}
        {showPaymentModal && selectedBilling && (
          <div className="fixed inset-0 bg-black/50 flex items-center justify-center p-4 z-50">
            <div className="bg-white rounded-xl max-w-md w-full p-6">
              <h2 className="text-2xl font-bold text-gray-900 mb-6">Process Payment</h2>

              <div className="mb-6 p-4 bg-gray-50 rounded-lg">
                <p className="text-sm text-gray-600 mb-2">Billing Amount</p>
                <p className="text-3xl font-bold text-purple-600">
                  ₹{(selectedBilling.totalAmount / 100).toFixed(2)}
                </p>
              </div>

              {/* Payment Method Selection */}
              <div className="mb-6">
                <p className="font-medium text-gray-900 mb-3">Payment Method</p>
                <div className="space-y-2">
                  <label className="flex items-center p-3 border border-purple-300 rounded-lg cursor-pointer hover:bg-purple-50">
                    <input
                      type="radio"
                      value="razorpay"
                      checked={paymentMethod === 'razorpay'}
                      onChange={(e) => setPaymentMethod(e.target.value)}
                      className="w-4 h-4"
                    />
                    <span className="ml-3 font-medium text-gray-900">Online Payment (Razorpay)</span>
                  </label>
                  <label className="flex items-center p-3 border border-gray-300 rounded-lg cursor-pointer hover:bg-gray-50">
                    <input
                      type="radio"
                      value="cash"
                      checked={paymentMethod === 'cash'}
                      onChange={(e) => setPaymentMethod(e.target.value)}
                      className="w-4 h-4"
                    />
                    <span className="ml-3 font-medium text-gray-900">Cash Payment</span>
                  </label>
                </div>
              </div>

              {/* Cash Payment Fields */}
              {paymentMethod === 'cash' && (
                <div className="mb-6 space-y-4">
                  <div>
                    <label className="block text-sm font-medium text-gray-900 mb-2">Amount Received</label>
                    <input
                      type="number"
                      value={amount}
                      onChange={(e) => setAmount(e.target.value)}
                      className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-500"
                      placeholder="Enter amount"
                    />
                  </div>
                  <div>
                    <label className="block text-sm font-medium text-gray-900 mb-2">Change Given</label>
                    <input
                      type="number"
                      value={changeGiven}
                      onChange={(e) => setChangeGiven(e.target.value)}
                      className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-500"
                      placeholder="Enter change"
                    />
                  </div>
                </div>
              )}

              <div className="flex gap-3">
                <button
                  onClick={() => handleInitiatePayment(selectedBilling.id, selectedBilling.totalAmount)}
                  disabled={isProcessing}
                  className="flex-1 bg-purple-600 hover:bg-purple-700 text-white py-2 rounded-lg font-medium transition-colors disabled:opacity-50"
                >
                  {isProcessing ? 'Processing...' : 'Pay Now'}
                </button>
                <button
                  onClick={() => setShowPaymentModal(false)}
                  className="flex-1 bg-gray-300 hover:bg-gray-400 text-gray-900 py-2 rounded-lg font-medium transition-colors"
                >
                  Cancel
                </button>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default PaymentsPage;