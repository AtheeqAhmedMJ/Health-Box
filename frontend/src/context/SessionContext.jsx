import { createContext, useContext, useState } from "react";

const SessionContext = createContext();

export const SessionProvider = ({ children }) => {

  const [session, setSession] = useState({
    user: null,
    clinic: null,
    doctor: null,
    date: new Date().toISOString().split("T")[0],
    activeAppointment: null
  });

  const updateSession = (data) => {
    setSession(prev => ({ ...prev, ...data }));
  };

  return (
    <SessionContext.Provider value={{ session, updateSession }}>
      {children}
    </SessionContext.Provider>
  );
};

export const useSession = () => useContext(SessionContext);