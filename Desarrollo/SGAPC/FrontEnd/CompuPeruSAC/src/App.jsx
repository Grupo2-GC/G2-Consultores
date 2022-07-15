import React, { useEffect, useState } from "react";
import { Navigate, Route, Routes } from "react-router-dom";
import { AuthProvider } from "./context/auth/AuthProvider";
import { ColaboradorProvider } from "./context/colaboradores/ColaboradorProvider";
import Login from "./pages/Login";
import Sistema from "./pages/Sistema";
import AlmacenArticulos from "./private/AlmacenArticulos";
import AlmacenCategorias from "./private/AlmacenCategorias";
import CrearPersonal from "./private/CrearPersonal";
import Estritorio from "./private/Estritorio";
import ListarPersonal from "./private/ListarPersonal";

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
      <ColaboradorProvider>
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
            >
              <Route index element={<Estritorio />} />
              <Route path="addpersonal" element={<CrearPersonal />} />
              <Route path="personal" element={<ListarPersonal />} />
              <Route path="articulos" element={<AlmacenArticulos />} />
              <Route path="categorias" element={<AlmacenCategorias />} />
            </Route>
          </>
        )}
        <Route path="*" element={<Navigate to={auth ? "/sistema" : "/"} />} />
      </Routes>
      </ColaboradorProvider>
    </AuthProvider>
  );
};

export default App;
