import React, { useEffect, useState } from "react";
import { Navigate, Route, Routes } from "react-router-dom";
import { AuthProvider } from "./context/auth/AuthProvider";
import Login from "./pages/Login";
import Sistema from "./pages/Sistema";

const App = () => {
  const [auth, setAuth] = useState(null);
  useEffect(() => {
    let token = localStorage.getItem("token");
    token ? setAuth(true) : setAuth(false);
  }, []);

  const authLogin = () => {
    setAuth(true);
  };
  const logout = () => {
    setAuth(false);
  };

  return (
    <AuthProvider>
      <Routes>
        {/* Rutas Publicas */}
        {!auth && (
          <>
            <Route path="/" element={<Login authLogin={authLogin} />} />
            ç
          </>
        )}
        {/* Rutas Privadas */}
        {auth && (
          <>
            <Route
              path="/sistema"
              element={<Sistema logout={logout} />}
            ></Route>
          </>
        )}
        <Route path="*" element={<Navigate to={auth ? "/sistema" : "/"} />} />
      </Routes>
    </AuthProvider>
  );
};

export default App;
