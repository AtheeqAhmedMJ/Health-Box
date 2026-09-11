import React, { useEffect } from 'react';
import { useApp } from '../../context/AppContext';
import {
  FiCheckCircle,
  FiAlertCircle,
  FiInfo,
  FiX,
} from 'react-icons/fi';
import { NOTIFICATION_TYPES } from '../../constants';

/**
 * Individual Toast Notification
 */
const Toast = ({ notification }) => {
  const { removeNotification } = useApp();

  useEffect(() => {
    const timer = setTimeout(() => {
      removeNotification(notification.id);
    }, 3000);

    return () => clearTimeout(timer);
  }, [notification.id, removeNotification]);

  const getIcon = () => {
    switch (notification.type) {
      case NOTIFICATION_TYPES.SUCCESS:
        return <FiCheckCircle size={20} />;
      case NOTIFICATION_TYPES.ERROR:
        return <FiAlertCircle size={20} />;
      case NOTIFICATION_TYPES.WARNING:
        return <FiAlertCircle size={20} />;
      case NOTIFICATION_TYPES.INFO:
        return <FiInfo size={20} />;
      default:
        return <FiInfo size={20} />;
    }
  };

  const getStyles = () => {
    switch (notification.type) {
      case NOTIFICATION_TYPES.SUCCESS:
        return {
          bg: 'bg-green-50',
          border: 'border-green-200',
          icon: 'text-green-600',
          text: 'text-green-800',
        };
      case NOTIFICATION_TYPES.ERROR:
        return {
          bg: 'bg-red-50',
          border: 'border-red-200',
          icon: 'text-red-600',
          text: 'text-red-800',
        };
      case NOTIFICATION_TYPES.WARNING:
        return {
          bg: 'bg-yellow-50',
          border: 'border-yellow-200',
          icon: 'text-yellow-600',
          text: 'text-yellow-800',
        };
      case NOTIFICATION_TYPES.INFO:
        return {
          bg: 'bg-blue-50',
          border: 'border-blue-200',
          icon: 'text-blue-600',
          text: 'text-blue-800',
        };
      default:
        return {
          bg: 'bg-gray-50',
          border: 'border-gray-200',
          icon: 'text-gray-600',
          text: 'text-gray-800',
        };
    }
  };

  const styles = getStyles();

  return (
    <div
      className={`${styles.bg} ${styles.border} border rounded-lg p-4 shadow-lg pointer-events-auto flex items-start gap-3 min-w-[300px] animate-in slide-in-from-right-full duration-300`}
    >
      <div className={styles.icon}>{getIcon()}</div>

      <div className="flex-1">
        <p className={`${styles.text} text-sm font-medium`}>
          {notification.message}
        </p>
      </div>

      <button
        onClick={() => removeNotification(notification.id)}
        className={`${styles.icon} hover:opacity-75 transition-opacity flex-shrink-0`}
      >
        <FiX size={18} />
      </button>
    </div>
  );
};

/**
 * Notification Container - displays all notifications
 */
const NotificationContainer = () => {
  const { notifications } = useApp();

  return (
    <div className="fixed top-6 right-6 z-50 space-y-3 pointer-events-none">
      {notifications.map(notification => (
        <Toast key={notification.id} notification={notification} />
      ))}
    </div>
  );
};

export default NotificationContainer;
