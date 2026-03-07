import axios from "axios";

const API = "http://localhost:8080/api";

export const getDaySchedule = async (clinicId, date) => {
  const res = await axios.get(`${API}/calendar/day`, {
    params: {
      clinicId,
      date
    }
  });

  return res.data;
};
