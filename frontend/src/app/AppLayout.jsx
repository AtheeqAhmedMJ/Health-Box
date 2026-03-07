import SideBar from "../components/layout/SideBar";
import Background from "../components/backgrounds/Background";

const AppLayout = ({ children }) => {

  return (
    <>
      <SideBar />
      <div style={{ marginLeft: 100 }}>
        {children}
      </div>
      <Background />
    </>
  );
};

export default AppLayout;