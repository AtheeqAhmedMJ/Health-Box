import client from "./client";

export const createSlot = (payload) =>
  client.post("/slots", payload).then(r => r.data);