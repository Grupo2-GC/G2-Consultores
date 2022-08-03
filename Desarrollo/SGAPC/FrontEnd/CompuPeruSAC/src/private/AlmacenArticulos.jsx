import React, {useContext, useEffect, useState} from 'react'
import ModalConfirmaion from '../components/ModalConfirmaion'
// import { ColaboradorContext } from "../context/colaboradores/ColaboradorProvider";
// import { ErrorFormulario } from "../components/ErrorFormulario";
// import {ErrorFormLogin,Formulario,Box1,TitleForm,Hr,GridForm,GrupoForm,LabelForm,InputForm,SelectForm,BotonForm  } from "../styles/FormularioRegistrar"
import { ContenedorModulo, TitlePage } from '../styles/UsablesStyle'
const AlmacenArticulos = () => {
  const [modal, setModal] = useState(false);
  return (
    <>
    <ModalConfirmaion modal={modal} setModal={setModal} />
      <ContenedorModulo>
        <TitlePage tabla="tabla">
          <h1>Crear Producto</h1>
          <h4>Gestión Productos</h4>
        </TitlePage>
        <br />
        
      </ContenedorModulo>
    </>
  )
}

export default AlmacenArticulos