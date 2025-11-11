import React from "react";
import {
  LineChart,
  Line,
  CartesianGrid,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer,
} from "recharts";

const AppointmentGraph = ({ data }) => {
  return (
    <div style={{ width: "100%", height: 310, marginTop: "0rem" }}>
      <h4 style={{ textAlign: "center" }}>Appointments Over Time</h4>
      <ResponsiveContainer>
        <LineChart data={data}>
          <CartesianGrid stroke="#ccc" strokeDasharray="5 5" />
          <XAxis dataKey="day" />
          <YAxis />
          <Tooltip />
          <Line type="monotone" dataKey="appointments" stroke="#1e90ff" strokeWidth={2} />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
};

export default AppointmentGraph;
