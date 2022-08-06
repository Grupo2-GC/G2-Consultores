import React, { useContext } from 'react';
import { ColaboradorContext } from '../context/colaboradores/ColaboradorProvider';
import {Overlay,ContenedorModalBasico,CerrarModalStyle,BotonModal} from "../styles/ModalStyle";

const ModalDelete = ({modalDelete,setModalDelete}) => {
    const {idColaborador,eliminarColaborador,resetColaboradorActual} = useContext(ColaboradorContext)
  
    const CerrarModalDelete = () => {
        setModalDelete(false)
        resetColaboradorActual();
    }
    const Eliminar = () => {
      eliminarColaborador(idColaborador);
      setModalDelete(false)
        resetColaboradorActual();
    }
  return (
    <>
    {modalDelete && (<Overlay className="modalDelete">
      <ContenedorModalBasico>
        <CerrarModalStyle onClick={CerrarModalDelete}>X</CerrarModalStyle>
        <h3>Eliminar usuario</h3>
        <p>Estas seguro que quieres eliminar este usuario</p>
        <BotonModal deleteStyle="eliminar" onClick={Eliminar} >Si</BotonModal>
        <BotonModal onClick={CerrarModalDelete}>No</BotonModal>
      </ContenedorModalBasico>
    </Overlay>)}
  </>
  )
}

export default ModalDelete