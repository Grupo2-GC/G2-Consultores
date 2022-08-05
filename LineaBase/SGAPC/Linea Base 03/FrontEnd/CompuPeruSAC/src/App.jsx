import React, { useEffect, useState } from "react";
import { Navigate, Route, Routes } from "react-router-dom";
import { AuthProvider } from "./context/auth/AuthProvider";
import { ColaboradorProvider } from "./context/colaboradores/ColaboradorProvider";
import { ProductoProvider } from "./context/productos/ProductoProvider";
import Login from "./pages/Login";
import Sistema from "./pages/Sistema";
import AlmacenArticulos from "./private/AlmacenArticulos";
import AlmacenCategorias from "./private/AlmacenCategorias";
import CrearPersonal from "./private/CrearPersonal";
import CrearProducto from "./private/CrearProductos";
import Detalles from "./private/Detalles";
import Estritorio from "./private/Estritorio";
import ListadoOrdenes from "./private/ListadoOrdenes";
import ListarPersonal from "./private/ListarPersonal";
import ListarProductos from "./private/ListarProductos";

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
        <ProductoProvider>
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
                  <Route path="addProducto" element={<CrearProducto />} />
                  <Route path="listarproductos" element={<ListarProductos />} />
                  <Route path="categorias" element={<AlmacenCategorias />} />
                  <Route path='listarproductos/detalles/:id' element={<Detalles /> } />
                  <Route path="listarordenes" element={<ListadoOrdenes />} />
                </Route>
              </>
            )}
            <Route path="*" element={<Navigate to={auth ? "/sistema" : "/"} />} />
          </Routes>
        </ProductoProvider>
      </ColaboradorProvider>
    </AuthProvider>
  );
};

export default App;
