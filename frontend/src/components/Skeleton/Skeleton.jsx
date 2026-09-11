// src/components/Skeleton/Skeleton.jsx
import React from 'react';

/**
 * Skeleton Component - Placeholder while loading
 */
const Skeleton = ({ width = 'w-full', height = 'h-4', className = '', count = 1 }) => {
  return (
    <>
      {Array(count)
        .fill(0)
        .map((_, i) => (
          <div
            key={i}
            className={`${width} ${height} ${className} bg-gradient-to-r from-gray-200/50 to-gray-300/50 rounded animate-pulse mb-3 last:mb-0`}
          />
        ))}
    </>
  );
};

export default Skeleton;

// src/components/Skeleton/SkeletonCard.jsx
/**
 * Skeleton Card Component
 */
const SkeletonCard = ({ count = 3 }) => {
  return (
    <>
      {Array(count)
        .fill(0)
        .map((_, i) => (
          <div
            key={i}
            className="bg-white/40 backdrop-blur-md rounded-xl p-6 shadow-lg border border-white/20"
          >
            <div className="space-y-4">
              {/* Header */}
              <div className="flex justify-between items-start">
                <div className="space-y-2">
                  <Skeleton width="w-40" height="h-5" />
                  <Skeleton width="w-32" height="h-4" />
                </div>
                <Skeleton width="w-20" height="h-6" />
              </div>

              {/* Content */}
              <div className="space-y-3">
                <Skeleton width="w-full" height="h-4" />
                <Skeleton width="w-5/6" height="h-4" />
                <Skeleton width="w-4/5" height="h-4" />
              </div>

              {/* Footer */}
              <div className="flex gap-2 pt-2">
                <Skeleton width="w-24" height="h-8" />
                <Skeleton width="w-24" height="h-8" />
              </div>
            </div>
          </div>
        ))}
    </>
  );
};

// src/components/Skeleton/SkeletonTable.jsx
/**
 * Skeleton Table Component
 */
const SkeletonTable = ({ rows = 5, cols = 4 }) => {
  return (
    <div className="bg-white/40 backdrop-blur-md rounded-xl overflow-hidden border border-white/20">
      {/* Header */}
      <div className="grid bg-white/20 p-4" style={{ gridTemplateColumns: `repeat(${cols}, 1fr)` }}>
        {Array(cols)
          .fill(0)
          .map((_, i) => (
            <Skeleton key={i} width="w-full" height="h-4" />
          ))}
      </div>

      {/* Rows */}
      {Array(rows)
        .fill(0)
        .map((_, rowIdx) => (
          <div
            key={rowIdx}
            className="grid p-4 border-t border-white/10"
            style={{ gridTemplateColumns: `repeat(${cols}, 1fr)` }}
          >
            {Array(cols)
              .fill(0)
              .map((_, colIdx) => (
                <Skeleton key={colIdx} width="w-full" height="h-4" />
              ))}
          </div>
        ))}
    </div>
  );
};

// src/components/Skeleton/SkeletonDashboard.jsx
/**
 * Skeleton Dashboard Component
 */
const SkeletonDashboard = () => {
  return (
    <div className="space-y-8">
      {/* Stats Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        {Array(4)
          .fill(0)
          .map((_, i) => (
            <div
              key={i}
              className="bg-white/40 backdrop-blur-md rounded-xl p-6 shadow-lg border border-white/20"
            >
              <Skeleton width="w-24" height="h-4" />
              <div className="mt-4">
                <Skeleton width="w-32" height="h-8" />
              </div>
            </div>
          ))}
      </div>

      {/* Charts */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <div className="lg:col-span-2 bg-white/40 backdrop-blur-md rounded-xl p-6 shadow-lg border border-white/20">
          <Skeleton width="w-40" height="h-5" className="mb-6" />
          <Skeleton width="w-full" height="h-64" />
        </div>

        <div className="bg-white/40 backdrop-blur-md rounded-xl p-6 shadow-lg border border-white/20">
          <Skeleton width="w-40" height="h-5" className="mb-6" />
          <div className="space-y-3">
            {Array(4)
              .fill(0)
              .map((_, i) => (
                <Skeleton key={i} width="w-full" height="h-4" />
              ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export { SkeletonCard, SkeletonTable, SkeletonDashboard };
