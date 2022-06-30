import React, { useContext, useEffect, useState } from "react";
import Logo from "../assets/logoLogin.png";
import Robot from "../assets/robot2.png";
import Consultora from "../assets/consultoraLogin.png";
import { useNavigate } from "react-router-dom";
import {
  BoxError,
  BoxIngresar,
  ContenedorLogin,
  ErrorFormLogin,
  Form,
  FormBox,
  ImgConsultora,
  ImgRobot,
  LogoBox,
  MainContainer,
  RoboxBox,
  Sesion,
} from "../styles/LoginStyle";
import { AuthContext } from "../context/auth/AuthProvider";

const Login = ({ authLogin }) => {
  const navigate = useNavigate();
  const {
    iniciarSesion,
    mensaje,
    autenticado,
    ResetError,
    errorLogin,
  } = useContext(AuthContext);

  useEffect(() => {
    if (autenticado) {
      authLogin();
      navigate("sistema");
    }
    if (errorLogin) {
      ResetError();
    }
  }, [autenticado, navigate, errorLogin, ResetError]);

  const [usuario, setUsuario] = useState({
    usernameOrEmail: "",
    password: "",
  });
  const [error, setError] = useState(false);
  const [errorMensaje, setErrorMensaje] = useState("");
  //extraer usuario
  const { usernameOrEmail, password } = usuario;
  const handleChange = (e) => {
    setUsuario({
      ...usuario,
      [e.target.name]: e.target.value,
    });
  };
  const handleSubmit = (e) => {
    e.preventDefault();
    //validar que no haya campos vacios
    if (usernameOrEmail.trim() === "" || password.trim() === "") {
      setError(true);
      setErrorMensaje("Todos los campos son obligatorios");
      setTimeout(() => {
        setError(false);
      }, 5000);
      return;
    }
    if (password.length < 5) {
      setError(true);
      setErrorMensaje("Password es incorrecta");
      setTimeout(() => {
        setError(false);
        setErrorMensaje("");
      }, 5000);
      return;
    }
    // console.log({ usernameOrEmail, password });
    iniciarSesion({ usernameOrEmail, password });
  };
  return (
    <ContenedorLogin>
      <MainContainer>
        <LogoBox>
          <img src={Logo} alt="logo" />
        </LogoBox>
        <BoxIngresar>
          <RoboxBox>
            <ImgRobot>
              <img src={Robot} alt="logo" />
            </ImgRobot>
          </RoboxBox>
          <Sesion>
            <h3>Ingresar al sistema</h3>
            <br />
            <Form>
              <FormBox>
                <input
                  type="text"
                  id="usernameOrEmail"
                  name="usernameOrEmail"
                  placeholder="Usuario"
                  value={usernameOrEmail}
                  onChange={handleChange}
                />
                <i className="far fa-user-circle" aria-hidden="true"></i>
              </FormBox>
              <FormBox>
                <input
                  type="password"
                  id="password"
                  name="password"
                  placeholder="Contraseña"
                  value={password}
                  onChange={handleChange}
                />
                <i className="fas fa-lock" aria-hidden="true"></i>
              </FormBox>
              <FormBox>
                <input
                  className="btn"
                  type="submit"
                  value="Login"
                  onClick={handleSubmit}
                />
              </FormBox>

              {errorLogin ? (
                <ErrorFormLogin>
                  <BoxError>
                  <h3> <i className="fas fa-exclamation-circle" aria-hidden="true"></i>&nbsp;&nbsp; {mensaje}</h3>
                  </BoxError>
                </ErrorFormLogin>
              ) : null}
              {error ? (
                <ErrorFormLogin>
                  <BoxError>
                  <h3 >
                    <i
                      className="fas fa-exclamation-circle"
                      aria-hidden="true"
                    ></i>
                    &nbsp;&nbsp;{errorMensaje}
                  </h3>
                  </BoxError>
                </ErrorFormLogin>
              ) : null}
            </Form>
          </Sesion>
          <ImgConsultora>
          <img src={Consultora} alt="logo" />
          </ImgConsultora>
        </BoxIngresar>
      </MainContainer>
    </ContenedorLogin>
  );
};

export default Login;
