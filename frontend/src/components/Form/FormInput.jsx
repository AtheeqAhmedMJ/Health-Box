// src/components/Form/FormInput.jsx
import React from 'react';

/**
 * Reusable Form Input Component
 */
const FormInput = React.forwardRef(({
  label,
  type = 'text',
  placeholder,
  error,
  touched,
  icon: Icon,
  required = false,
  disabled = false,
  ...props
}, ref) => {
  return (
    <div>
      {label && (
        <label className="block text-sm font-semibold text-gray-700 mb-2">
          {label}
          {required && <span className="text-red-500 ml-1">*</span>}
        </label>
      )}

      <div className="relative">
        {Icon && (
          <Icon className="absolute left-4 top-1/2 -translate-y-1/2 text-gray-400" size={20} />
        )}

        <input
          ref={ref}
          type={type}
          placeholder={placeholder}
          disabled={disabled}
          className={`w-full ${Icon ? 'pl-12' : 'pl-4'} pr-4 py-3 bg-white/50 border rounded-lg focus:outline-none focus:ring-2 transition-all ${
            error && touched
              ? 'border-red-300 focus:ring-red-500'
              : 'border-white/30 focus:ring-purple-500'
          } ${disabled ? 'opacity-50 cursor-not-allowed' : ''}`}
          {...props}
        />
      </div>

      {error && touched && (
        <p className="text-red-600 text-sm mt-2">{error}</p>
      )}
    </div>
  );
});

FormInput.displayName = 'FormInput';
export default FormInput;

// src/components/Form/FormSelect.jsx
/**
 * Reusable Form Select Component
 */
const FormSelect = React.forwardRef(({
  label,
  options,
  placeholder,
  error,
  touched,
  required = false,
  disabled = false,
  ...props
}, ref) => {
  return (
    <div>
      {label && (
        <label className="block text-sm font-semibold text-gray-700 mb-2">
          {label}
          {required && <span className="text-red-500 ml-1">*</span>}
        </label>
      )}

      <select
        ref={ref}
        disabled={disabled}
        className={`w-full px-4 py-3 bg-white/50 border rounded-lg focus:outline-none focus:ring-2 transition-all ${
          error && touched
            ? 'border-red-300 focus:ring-red-500'
            : 'border-white/30 focus:ring-purple-500'
        } ${disabled ? 'opacity-50 cursor-not-allowed' : ''}`}
        {...props}
      >
        {placeholder && <option value="">{placeholder}</option>}
        {options.map(option => (
          <option key={option.value} value={option.value}>
            {option.label}
          </option>
        ))}
      </select>

      {error && touched && (
        <p className="text-red-600 text-sm mt-2">{error}</p>
      )}
    </div>
  );
});

FormSelect.displayName = 'FormSelect';
export default FormSelect;

// src/components/Form/FormTextarea.jsx
/**
 * Reusable Form Textarea Component
 */
const FormTextarea = React.forwardRef(({
  label,
  placeholder,
  error,
  touched,
  required = false,
  disabled = false,
  rows = 4,
  ...props
}, ref) => {
  return (
    <div>
      {label && (
        <label className="block text-sm font-semibold text-gray-700 mb-2">
          {label}
          {required && <span className="text-red-500 ml-1">*</span>}
        </label>
      )}

      <textarea
        ref={ref}
        placeholder={placeholder}
        disabled={disabled}
        rows={rows}
        className={`w-full px-4 py-3 bg-white/50 border rounded-lg focus:outline-none focus:ring-2 transition-all resize-none ${
          error && touched
            ? 'border-red-300 focus:ring-red-500'
            : 'border-white/30 focus:ring-purple-500'
        } ${disabled ? 'opacity-50 cursor-not-allowed' : ''}`}
        {...props}
      />

      {error && touched && (
        <p className="text-red-600 text-sm mt-2">{error}</p>
      )}
    </div>
  );
});

FormTextarea.displayName = 'FormTextarea';
export default FormTextarea;

// src/components/Form/FormCheckbox.jsx
/**
 * Reusable Form Checkbox Component
 */
const FormCheckbox = React.forwardRef(({
  label,
  error,
  disabled = false,
  ...props
}, ref) => {
  return (
    <div>
      <label className="flex items-center gap-3 cursor-pointer">
        <input
          ref={ref}
          type="checkbox"
          disabled={disabled}
          className={`w-5 h-5 rounded border-2 border-white/30 focus:ring-2 focus:ring-purple-500 cursor-pointer ${
            disabled ? 'opacity-50 cursor-not-allowed' : ''
          }`}
          {...props}
        />
        {label && <span className="text-sm font-medium text-gray-700">{label}</span>}
      </label>

      {error && (
        <p className="text-red-600 text-sm mt-2">{error}</p>
      )}
    </div>
  );
});

FormCheckbox.displayName = 'FormCheckbox';
export default FormCheckbox;

// src/components/Form/FormRadio.jsx
/**
 * Reusable Form Radio Component
 */
const FormRadio = React.forwardRef(({
  label,
  options,
  error,
  disabled = false,
  ...props
}, ref) => {
  return (
    <div>
      {label && (
        <label className="block text-sm font-semibold text-gray-700 mb-3">
          {label}
        </label>
      )}

      <div className="space-y-2">
        {options.map(option => (
          <label key={option.value} className="flex items-center gap-3 cursor-pointer">
            <input
              ref={ref}
              type="radio"
              value={option.value}
              disabled={disabled}
              className={`w-5 h-5 cursor-pointer ${
                disabled ? 'opacity-50 cursor-not-allowed' : ''
              }`}
              {...props}
            />
            <span className="text-sm font-medium text-gray-700">{option.label}</span>
          </label>
        ))}
      </div>

      {error && (
        <p className="text-red-600 text-sm mt-2">{error}</p>
      )}
    </div>
  );
});

FormRadio.displayName = 'FormRadio';
export default FormRadio;

// src/components/Form/FormButton.jsx
/**
 * Reusable Form Button Component
 */
const FormButton = ({
  children,
  type = 'button',
  variant = 'primary',
  size = 'md',
  disabled = false,
  loading = false,
  fullWidth = false,
  icon: Icon,
  ...props
}) => {
  const variants = {
    primary: 'bg-gradient-to-r from-purple-500 to-pink-500 text-white hover:from-purple-600 hover:to-pink-600',
    secondary: 'bg-white/40 border border-white/20 text-gray-900 hover:bg-white/60',
    danger: 'bg-red-500 text-white hover:bg-red-600',
    success: 'bg-green-500 text-white hover:bg-green-600',
  };

  const sizes = {
    sm: 'px-3 py-2 text-sm',
    md: 'px-4 py-3 text-base',
    lg: 'px-6 py-4 text-lg',
  };

  return (
    <button
      type={type}
      disabled={disabled || loading}
      className={`
        font-semibold rounded-lg transition-all transform
        ${variants[variant]}
        ${sizes[size]}
        ${fullWidth ? 'w-full' : ''}
        ${disabled || loading ? 'opacity-50 cursor-not-allowed' : 'hover:scale-105 active:scale-95'}
        flex items-center justify-center gap-2
      `}
      {...props}
    >
      {loading && (
        <div className="animate-spin rounded-full h-4 w-4 border-b-2 border-current" />
      )}
      {Icon && !loading && <Icon size={20} />}
      {children}
    </button>
  );
};

export default FormButton;
