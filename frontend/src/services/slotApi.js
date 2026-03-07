const API = "http://localhost:8080/api/slots";

export const createSlot = async (slot) => {
  const res = await fetch(API, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(slot),
  });

  if (!res.ok) throw new Error("Failed to create slot");

  return res.json();
};
