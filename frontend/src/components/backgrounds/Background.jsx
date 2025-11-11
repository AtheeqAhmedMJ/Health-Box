import React from 'react';

const Background = () => {
  return (
    <div className="background">
      <div className="dune" />
      <style jsx>{`
        .background {
          z-index: -1;
          position: fixed;
          inset: 0;
          z-index: -10;
          overflow: hidden;
          background: linear-gradient(to bottom right, #d794d0, #d9a8e1, #d9b3e2);
        }

        .dune {
          z-index: -1;
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
          animation: waveMotion 8s ease-in-out infinite alternate;
          filter: blur(2px);
        }

        .dune::before {
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
          animation: waveMotionBefore 12s ease-in-out infinite alternate-reverse;
          filter: blur(3px);
        }

        .dune::after {
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
          animation: waveMotionAfter 10s ease-in-out infinite alternate;
          animation-delay: -2s;
          filter: blur(1px);
        }

        @keyframes waveMotionBefore {
          0% {
            clip-path: ellipse(85% 100% at 100% 50%);
          }
          50% {
            clip-path: ellipse(87% 102% at 100% 48%);
          }
          100% {
            clip-path: ellipse(83% 98% at 100% 52%);
          }
        }

        @keyframes waveMotionAfter {
          0% {
            clip-path: ellipse(75% 100% at 100% 50%);
          }
          50% {
            clip-path: ellipse(77% 103% at 100% 47%);
          }
          100% {
            clip-path: ellipse(73% 97% at 100% 53%);
          }
        }

        @keyframes waveMotion {
          0% {
            clip-path: ellipse(80% 100% at 100% 50%);
          }
          25% {
            clip-path: ellipse(82% 98% at 100% 48%);
          }
          50% {
            clip-path: ellipse(78% 102% at 100% 52%);
          }
          75% {
            clip-path: ellipse(83% 99% at 100% 49%);
          }
          100% {
            clip-path: ellipse(79% 101% at 100% 51%);
          }
        }

        /* Responsive Design */
        
        /* Large Desktop (1920px+) */
        @media (min-width: 1920px) {
          .dune {
            width: 55%;
            clip-path: ellipse(85% 100% at 100% 50%);
          }
        }

        /* Desktop (1200px - 1919px) */
        @media (min-width: 1200px) and (max-width: 1919px) {
          .dune {
            width: 60%;
            clip-path: ellipse(80% 100% at 100% 50%);
          }
        }

        /* Laptop (992px - 1199px) */
        @media (min-width: 992px) and (max-width: 1199px) {
          .dune {
            width: 65%;
            clip-path: ellipse(75% 100% at 100% 50%);
          }
        }

        /* Tablet Landscape (768px - 991px) */
        @media (min-width: 768px) and (max-width: 991px) {
          .dune {
            width: 70%;
            animation-duration: 10s;
          }
          @keyframes waveMotion {
            0% {
              clip-path: ellipse(70% 100% at 100% 50%);
            }
            50% {
              clip-path: ellipse(72% 102% at 100% 48%);
            }
            100% {
              clip-path: ellipse(68% 98% at 100% 52%);
            }
          }
          @keyframes waveMotionBefore {
            0% {
              clip-path: ellipse(75% 100% at 100% 50%);
            }
            50% {
              clip-path: ellipse(77% 102% at 100% 48%);
            }
            100% {
              clip-path: ellipse(73% 98% at 100% 52%);
            }
          }
          @keyframes waveMotionAfter {
            0% {
              clip-path: ellipse(65% 100% at 100% 50%);
            }
            50% {
              clip-path: ellipse(67% 103% at 100% 47%);
            }
            100% {
              clip-path: ellipse(63% 97% at 100% 53%);
            }
          }
        }

        /* Tablet Portrait (576px - 767px) */
        @media (min-width: 576px) and (max-width: 767px) {
          .dune {
            width: 75%;
            animation-duration: 12s;
            filter: blur(1.5px);
          }
          @keyframes waveMotion {
            0% {
              clip-path: ellipse(65% 100% at 100% 50%);
            }
            50% {
              clip-path: ellipse(67% 102% at 100% 48%);
            }
            100% {
              clip-path: ellipse(63% 98% at 100% 52%);
            }
          }
          @keyframes waveMotionBefore {
            0% {
              clip-path: ellipse(70% 100% at 100% 50%);
            }
            50% {
              clip-path: ellipse(72% 102% at 100% 48%);
            }
            100% {
              clip-path: ellipse(68% 98% at 100% 52%);
            }
          }
          @keyframes waveMotionAfter {
            0% {
              clip-path: ellipse(60% 100% at 100% 50%);
            }
            50% {
              clip-path: ellipse(62% 103% at 100% 47%);
            }
            100% {
              clip-path: ellipse(58% 97% at 100% 53%);
            }
          }
        }

        /* Mobile Landscape (480px - 575px) */
        @media (min-width: 480px) and (max-width: 575px) {
          .dune {
            width: 80%;
            animation-duration: 14s;
            filter: blur(1px);
          }
          @keyframes waveMotion {
            0% {
              clip-path: ellipse(60% 100% at 100% 50%);
            }
            50% {
              clip-path: ellipse(62% 101% at 100% 49%);
            }
            100% {
              clip-path: ellipse(58% 99% at 100% 51%);
            }
          }
          @keyframes waveMotionBefore {
            0% {
              clip-path: ellipse(65% 100% at 100% 50%);
            }
            50% {
              clip-path: ellipse(67% 101% at 100% 49%);
            }
            100% {
              clip-path: ellipse(63% 99% at 100% 51%);
            }
          }
          @keyframes waveMotionAfter {
            0% {
              clip-path: ellipse(55% 100% at 100% 50%);
            }
            50% {
              clip-path: ellipse(57% 102% at 100% 48%);
            }
            100% {
              clip-path: ellipse(53% 98% at 100% 52%);
            }
          }
        }

        /* Mobile Portrait (320px - 479px) */
        @media (max-width: 479px) {
          .dune {
            width: 85%;
            animation-duration: 16s;
            filter: blur(0.8px);
          }
          @keyframes waveMotion {
            0% {
              clip-path: ellipse(55% 100% at 100% 50%);
            }
            50% {
              clip-path: ellipse(57% 101% at 100% 49%);
            }
            100% {
              clip-path: ellipse(53% 99% at 100% 51%);
            }
          }
          @keyframes waveMotionBefore {
            0% {
              clip-path: ellipse(60% 100% at 100% 50%);
            }
            50% {
              clip-path: ellipse(62% 101% at 100% 49%);
            }
            100% {
              clip-path: ellipse(58% 99% at 100% 51%);
            }
          }
          @keyframes waveMotionAfter {
            0% {
              clip-path: ellipse(50% 100% at 100% 50%);
            }
            50% {
              clip-path: ellipse(52% 102% at 100% 48%);
            }
            100% {
              clip-path: ellipse(48% 98% at 100% 52%);
            }
          }
        }

        /* Ultra Small Mobile (< 320px) */
        @media (max-width: 319px) {
          .dune {
            width: 90%;
            animation-duration: 18s;
            filter: blur(0.5px);
          }
          @keyframes waveMotion {
            0% {
              clip-path: ellipse(50% 100% at 100% 50%);
            }
            50% {
              clip-path: ellipse(52% 101% at 100% 49%);
            }
            100% {
              clip-path: ellipse(48% 99% at 100% 51%);
            }
          }
          @keyframes waveMotionBefore {
            0% {
              clip-path: ellipse(55% 100% at 100% 50%);
            }
            50% {
              clip-path: ellipse(57% 101% at 100% 49%);
            }
            100% {
              clip-path: ellipse(53% 99% at 100% 51%);
            }
          }
          @keyframes waveMotionAfter {
            0% {
              clip-path: ellipse(45% 100% at 100% 50%);
            }
            50% {
              clip-path: ellipse(47% 102% at 100% 48%);
            }
            100% {
              clip-path: ellipse(43% 98% at 100% 52%);
            }
          }
        }

        /* Orientation-specific adjustments */
        @media (orientation: portrait) {
          .dune {
            animation-duration: calc(8s + 2s);
          }
          .dune::before {
            animation-duration: calc(12s + 3s);
          }
          .dune::after {
            animation-duration: calc(10s + 2s);
          }
        }

        @media (orientation: landscape) and (max-height: 500px) {
          .dune {
            width: 70%;
          }
          @keyframes waveMotion {
            0% {
              clip-path: ellipse(70% 100% at 100% 50%);
            }
            50% {
              clip-path: ellipse(72% 102% at 100% 48%);
            }
            100% {
              clip-path: ellipse(68% 98% at 100% 52%);
            }
          }
        }

        /* High DPI/Retina displays */
        @media (-webkit-min-device-pixel-ratio: 2), (min-resolution: 192dpi) {
          .dune {
            filter: blur(1.5px);
          }
          .dune::before {
            filter: blur(2px);
          }
          .dune::after {
            filter: blur(1px);
          }
        }

        /* Reduced motion for accessibility */
        @media (prefers-reduced-motion: reduce) {
          .dune,
          .dune::before,
          .dune::after {
            animation-duration: 30s;
          }
          @keyframes waveMotion {
            0% {
              clip-path: ellipse(80% 100% at 100% 50%);
            }
            100% {
              clip-path: ellipse(81% 101% at 100% 49%);
            }
          }
          @keyframes waveMotionBefore {
            0% {
              clip-path: ellipse(85% 100% at 100% 50%);
            }
            100% {
              clip-path: ellipse(86% 101% at 100% 49%);
            }
          }
          @keyframes waveMotionAfter {
            0% {
              clip-path: ellipse(75% 100% at 100% 50%);
            }
            100% {
              clip-path: ellipse(76% 101% at 100% 49%);
            }
          }
        }
      `}</style>
    </div>
  );
};

export default Background;