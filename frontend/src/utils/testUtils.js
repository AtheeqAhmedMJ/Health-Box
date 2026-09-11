// src/utils/testUtils.js
import axios from 'axios';

/**
 * Mock API Response
 */
export const mockApiResponse = (data, delay = 0) => {
  return new Promise(resolve => {
    setTimeout(() => resolve({ data }), delay);
  });
};

/**
 * Mock API Error
 */
export const mockApiError = (message, statusCode = 500, delay = 0) => {
  return new Promise((_, reject) => {
    setTimeout(() => {
      const error = new Error(message);
      error.response = {
        status: statusCode,
        data: { message },
      };
      reject(error);
    }, delay);
  });
};

/**
 * Mock local storage
 */
export const setupLocalStorageMock = () => {
  const store = {};
  return {
    getItem: (key) => store[key] || null,
    setItem: (key, value) => {
      store[key] = value.toString();
    },
    removeItem: (key) => {
      delete store[key];
    },
    clear: () => {
      Object.keys(store).forEach(key => delete store[key]);
    },
  };
};

/**
 * Mock data generators
 */
export const generateMockUser = (overrides = {}) => {
  return {
    id: 'user-1',
    name: 'John Doe',
    email: 'john@example.com',
    phone: '9876543210',
    role: 'ADMIN',
    createdAt: new Date().toISOString(),
    ...overrides,
  };
};

export const generateMockAppointment = (overrides = {}) => {
  return {
    id: 'apt-1',
    patientPhone: '9876543210',
    doctorPhone: '9876543211',
    appointmentDate: new Date().toISOString().split('T')[0],
    appointmentTime: '10:00',
    reason: 'Checkup',
    status: 'SCHEDULED',
    createdAt: new Date().toISOString(),
    ...overrides,
  };
};

export const generateMockPatient = (overrides = {}) => {
  return {
    id: 'pat-1',
    name: 'Jane Doe',
    phone: '9876543210',
    email: 'jane@example.com',
    age: 30,
    address: '123 Main St',
    createdAt: new Date().toISOString(),
    ...overrides,
  };
};

export const generateMockPayment = (overrides = {}) => {
  return {
    id: 'pay-1',
    amount: 5000,
    currency: 'INR',
    status: 'COMPLETED',
    paymentMethod: 'CARD',
    orderId: 'ord-1',
    createdAt: new Date().toISOString(),
    ...overrides,
  };
};

/**
 * Wait for async operations
 */
export const waitFor = (condition, timeout = 5000) => {
  return new Promise((resolve, reject) => {
    const startTime = Date.now();
    const checkInterval = setInterval(() => {
      if (condition()) {
        clearInterval(checkInterval);
        resolve();
      } else if (Date.now() - startTime > timeout) {
        clearInterval(checkInterval);
        reject(new Error('Timeout waiting for condition'));
      }
    }, 50);
  });
};

/**
 * Render component with providers (for testing)
 */
export const renderWithProviders = (component, { route = '/' } = {}) => {
  window.history.pushState({}, 'Test page', route);
  // Implementation depends on your testing framework
  return component;
};

/**
 * Create mock navigation
 */
export const createMockNavigate = () => {
  const navigate = jest.fn();
  navigate.mockResolvedValue(undefined);
  return navigate;
};

/**
 * Cleanup after tests
 */
export const cleanup = () => {
  localStorage.clear();
  sessionStorage.clear();
  jest.clearAllMocks();
};
