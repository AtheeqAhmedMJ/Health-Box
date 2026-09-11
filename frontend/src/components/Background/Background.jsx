import React from 'react';

// Ambient animated background — soft purple gradient with a slowly
// breathing "dune" of light sweeping in from the right.
const Background = () => {
  return (
    <div className="hbx-background">
      <div className="hbx-dune" />
      <style>{`
        .hbx-background {
          position: fixed;
          inset: 0;
          z-index: -10;
          overflow: hidden;
          background: linear-gradient(to bottom right, #d794d0, #d9a8e1, #d9b3e2);
        }

        .hbx-dune {
          position: absolute;
          top: 0;
          right: 0;
          width: 60%;
          height: 100%;
          background: linear-gradient(to left,
            rgba(255, 255, 255, 0.8) 0%,
            rgba(255, 255, 255, 0.6) 30%,
            rgba(255, 255, 255, 0.3) 60%,
            rgba(255, 255, 255, 0.1) 80%,
            transparent 100%
          );
          clip-path: ellipse(80% 100% at 100% 50%);
          animation: hbxWave 8s ease-in-out infinite alternate;
          filter: blur(2px);
        }

        .hbx-dune::before {
          content: '';
          position: absolute;
          top: 0;
          right: 0;
          width: 100%;
          height: 100%;
          background: linear-gradient(to left,
            rgba(255, 255, 255, 0.4) 0%,
            rgba(255, 255, 255, 0.2) 40%,
            transparent 70%
          );
          animation: hbxWaveBefore 12s ease-in-out infinite alternate-reverse;
          filter: blur(3px);
        }

        .hbx-dune::after {
          content: '';
          position: absolute;
          top: 0;
          right: 0;
          width: 100%;
          height: 100%;
          background: linear-gradient(to left,
            rgba(255, 255, 255, 0.6) 0%,
            rgba(255, 255, 255, 0.3) 50%,
            transparent 80%
          );
          animation: hbxWaveAfter 10s ease-in-out infinite alternate;
          animation-delay: -2s;
          filter: blur(1px);
        }

        @keyframes hbxWave {
          0% { clip-path: ellipse(80% 100% at 100% 50%); }
          25% { clip-path: ellipse(82% 98% at 100% 48%); }
          50% { clip-path: ellipse(78% 102% at 100% 52%); }
          75% { clip-path: ellipse(83% 99% at 100% 49%); }
          100% { clip-path: ellipse(79% 101% at 100% 51%); }
        }

        @keyframes hbxWaveBefore {
          0% { clip-path: ellipse(85% 100% at 100% 50%); }
          50% { clip-path: ellipse(87% 102% at 100% 48%); }
          100% { clip-path: ellipse(83% 98% at 100% 52%); }
        }

        @keyframes hbxWaveAfter {
          0% { clip-path: ellipse(75% 100% at 100% 50%); }
          50% { clip-path: ellipse(77% 103% at 100% 47%); }
          100% { clip-path: ellipse(73% 97% at 100% 53%); }
        }

        @media (max-width: 991px) {
          .hbx-dune { width: 70%; }
        }
        @media (max-width: 767px) {
          .hbx-dune { width: 80%; filter: blur(1.5px); }
        }
        @media (max-width: 479px) {
          .hbx-dune { width: 90%; filter: blur(1px); }
        }
        @media (prefers-reduced-motion: reduce) {
          .hbx-dune, .hbx-dune::before, .hbx-dune::after {
            animation-duration: 30s;
          }
        }
      `}</style>
    </div>
  );
};

export default Background;
