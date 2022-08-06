import React, { useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { clienteAxios } from '../config/axios';
import {Overlay,ContenedorModalBasico,CerrarModalStyle,BotonModal} from "../styles/ModalStyle";

const ModalDeleteProducto = ({modalDelete,setModalDelete,item}) => {
  
    const navigate = useNavigate();
    const CerrarModalDelete = () => {
        setModalDelete(false)
        
    }
    const Eliminar = async() => {
        await clienteAxios.delete(
            `/productos/${item._id}`
        );

      setModalDelete(false)
      navigate('/sistema/listarproductos');
    }
  return (
    <>
    {modalDelete && (<Overlay className="modalDelete">
      <ContenedorModalBasico>
        <CerrarModalStyle onClick={CerrarModalDelete}>X</CerrarModalStyle>
        <h3>Eliminar producto</h3>
        <p>Estas seguro que quieres eliminar este producto</p>
        <BotonModal deleteStyle="eliminar" onClick={Eliminar} >Si</BotonModal>
        <BotonModal onClick={CerrarModalDelete}>No</BotonModal>
      </ContenedorModalBasico>
    </Overlay>)}
  </>
  )
}

export default ModalDeleteProducto