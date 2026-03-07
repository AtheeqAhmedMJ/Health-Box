import client from "./client";

export const getDaySchedule = (clinicId, date) =>
  client.get("/calendar/day", {
    params: { clinicId, date }
  }).then(r => r.data);