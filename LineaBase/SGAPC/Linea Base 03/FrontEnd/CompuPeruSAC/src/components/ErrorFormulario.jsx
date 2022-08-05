import React, { useContext } from 'react';
import { ColaboradorContext } from '../context/colaboradores/ColaboradorProvider';
import { ErrorFormLogin } from '../styles/FormularioRegistrar';

export const ErrorFormulario = ({text}) => {
  
    const {errorForm , resetearErrores,errorFormulario  } = useContext(ColaboradorContext); 
    if (errorForm) {
        setTimeout(() => {
            resetearErrores();
        }, 4000);
    }
    console.log(errorFormulario);
  
  return <>
          {errorForm ? (
              <ErrorFormLogin>
              <h3><i className="fas fa-exclamation-circle" aria-hidden="true"></i>&nbsp;&nbsp;{errorFormulario}[verificar el correo, telefono o dni]</h3>
          </ErrorFormLogin>
          ): null}
          {text==="Error" ? (
              <ErrorFormLogin>
              <h3><i className="fas fa-exclamation-circle" aria-hidden="true"></i>&nbsp;&nbsp;[verificar el correo o telefono]</h3>
          </ErrorFormLogin>
          ): null}
        </>;
};
