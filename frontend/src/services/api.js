import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api';
const AUTH_BASE_URL = import.meta.env.VITE_AUTH_URL || 'http://localhost:8080/auth';

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Token management
export const setAuthToken = (token) => {
  if (token) {
    apiClient.defaults.headers.common['Authorization'] = `Bearer ${token}`;
    localStorage.setItem('authToken', token);
  } else {
    delete apiClient.defaults.headers.common['Authorization'];
    localStorage.removeItem('authToken');
  }
};

// Initialize token on app load
const storedToken = localStorage.getItem('authToken');
if (storedToken) {
  setAuthToken(storedToken);
}

// ==================== AUTH MODULE ====================
export const authAPI = {
  login: (credentials) => axios.post(`${AUTH_BASE_URL}/login`, credentials),
  
  registerHospital: (hospitalData) => 
    axios.post(`${AUTH_BASE_URL}/register-hospital`, hospitalData),
  
  registerPatient: (patientData) => 
    axios.post(`${AUTH_BASE_URL}/register-patient`, patientData),
  
  requestOTP: (email, purpose) =>
    axios.post(`${AUTH_BASE_URL}/otp/request`, { email, purpose }),
};

// ==================== APPOINTMENTS MODULE ====================
export const appointmentsAPI = {
  create: (appointmentData) => 
    apiClient.post('/appointments', appointmentData),
  
  getAll: () => 
    apiClient.get('/appointments'),
  
  getByPatient: (phoneNumber) => 
    apiClient.get(`/appointments/patient/${phoneNumber}`),
  
  getWithDetails: () => 
    apiClient.get('/appointments/with-details'),
  
  delete: (appointmentId) => 
    apiClient.delete(`/appointments/${appointmentId}`),
};

// ==================== CHARGES MODULE ====================
export const chargesAPI = {
  create: (chargeData) => 
    apiClient.post('/charges', chargeData),
  
  getAll: () => 
    apiClient.get('/charges'),
  
  getActive: () => 
    apiClient.get('/charges/active'),
  
  delete: (chargeId) => 
    apiClient.delete(`/charges/${chargeId}`),
};

// ==================== PATIENTS MODULE ====================
export const patientsAPI = {
  getAll: () => 
    apiClient.get('/patients'),
  
  getByPhone: (phoneNumber) => 
    apiClient.get(`/patients/${phoneNumber}`),
};

// ==================== PRESCRIPTIONS MODULE ====================
export const prescriptionsAPI = {
  getAll: () => 
    apiClient.get('/prescriptions'),
  
  getByPatient: (phoneNumber) => 
    apiClient.get(`/prescriptions/patient/${phoneNumber}`),
  
  getByAppointment: (appointmentId) => 
    apiClient.get(`/prescriptions/appointment/${appointmentId}`),
};

// ==================== BILLING MODULE ====================
export const billingAPI = {
  getAll: () => 
    apiClient.get('/billing'),
  
  getByPatient: (phoneNumber) => 
    apiClient.get(`/billing/patient/${phoneNumber}`),
};

// ==================== PAYMENTS MODULE ====================
export const paymentsAPI = {
  checkout: (paymentData) => 
    apiClient.post('/payments/checkout', paymentData),
  
  verify: (verifyData) => 
    apiClient.post('/payments/verify', verifyData),
  
  getAll: () => 
    apiClient.get('/payments'),
  
  getById: (paymentId) => 
    apiClient.get(`/payments/${paymentId}`),
  
  // Dev endpoints
  testSignVerify: (data) => 
    apiClient.post('/payments/_test/sign-verify', data),
  
  testSignWebhook: (data) => 
    apiClient.post('/payments/_test/sign-webhook', data),
  
  testSimulatePayment: (data) => 
    apiClient.post('/payments/_test/simulate-payment', data),
};

// ==================== PHARMACY MODULE ====================
export const pharmacyAPI = {
  attachToPrescription: (prescriptionId, data) => 
    apiClient.post(`/pharmacy/attach/${prescriptionId}`, data),
  
  dispense: (pharmacyId, data) => 
    apiClient.patch(`/pharmacy/${pharmacyId}/dispense`, data),
  
  getAll: () => 
    apiClient.get('/pharmacy'),
  
  getByPrescription: (prescriptionId) => 
    apiClient.get(`/pharmacy/prescription/${prescriptionId}`),
};

// ==================== SCHEDULING MODULE ====================
export const schedulingAPI = {
  create: (scheduleData) => 
    apiClient.post('/schedule', scheduleData),
  
  getAll: () => 
    apiClient.get('/schedule'),
  
  getByDoctor: (phoneNumber) => 
    apiClient.get(`/schedule/doctor/${phoneNumber}`),
  
  delete: (scheduleId) => 
    apiClient.delete(`/schedule/${scheduleId}`),
};

// ==================== DASHBOARD MODULE ====================
export const dashboardAPI = {
  getAdminSummary: () => 
    apiClient.get('/dashboard/summary'),
  
  getPatientSummary: () => 
    apiClient.get('/dashboard/patient-summary'),
};

// ==================== SUPERADMIN MODULE ====================
export const superadminAPI = {
  getStats: () => 
    apiClient.get('/superadmin/stats'),
};

export default apiClient;
