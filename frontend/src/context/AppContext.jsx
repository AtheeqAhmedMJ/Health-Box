// src/context/AppContext.jsx
import React, { createContext, useContext, useCallback, useState } from 'react';
import { NOTIFICATION_TYPES } from '../constants';

export const AppContext = createContext();

/**
 * App Context Provider with notifications and global state
 */
export const AppProvider = ({ children }) => {
  const [notifications, setNotifications] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [sidebarOpen, setSidebarOpen] = useState(false);

  /**
   * Show notification
   */
  const showNotification = useCallback((message, type = NOTIFICATION_TYPES.INFO, duration = 3000) => {
    const id = Date.now();
    const notification = { id, message, type };

    setNotifications(prev => [...prev, notification]);

    if (duration > 0) {
      setTimeout(() => {
        removeNotification(id);
      }, duration);
    }

    return id;
  }, []);

  /**
   * Remove specific notification
   */
  const removeNotification = useCallback((id) => {
    setNotifications(prev => prev.filter(n => n.id !== id));
  }, []);

  /**
   * Clear all notifications
   */
  const clearNotifications = useCallback(() => {
    setNotifications([]);
  }, []);

  /**
   * Convenience methods
   */
  const notify = {
    success: (message, duration) => showNotification(message, NOTIFICATION_TYPES.SUCCESS, duration),
    error: (message, duration) => showNotification(message, NOTIFICATION_TYPES.ERROR, duration),
    warning: (message, duration) => showNotification(message, NOTIFICATION_TYPES.WARNING, duration),
    info: (message, duration) => showNotification(message, NOTIFICATION_TYPES.INFO, duration),
  };

  const value = {
    notifications,
    showNotification,
    removeNotification,
    clearNotifications,
    notify,
    isLoading,
    setIsLoading,
    sidebarOpen,
    setSidebarOpen,
  };

  return <AppContext.Provider value={value}>{children}</AppContext.Provider>;
};

/**
 * Hook to use App Context
 */
export const useApp = () => {
  const context = useContext(AppContext);
  if (!context) {
    throw new Error('useApp must be used within AppProvider');
  }
  return context;
};
