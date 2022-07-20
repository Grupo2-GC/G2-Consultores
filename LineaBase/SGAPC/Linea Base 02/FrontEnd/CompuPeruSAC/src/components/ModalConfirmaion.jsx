import React from 'react'
import { BotonStyle, ContenedorModalBasico, Overlay } from '../styles/ModalStyle';

const ModalConfirmaion = ({modal,setModal}) => {
    const CerrarModal = () => {
        setModal(false)
    }

  return (
    <>
      {modal && (
      <Overlay>
        <ContenedorModalBasico>
          <h3>Usuario Registrado</h3>
          <p>Los datos del colaborador se guardaron con éxito</p>
          <BotonStyle onClick={CerrarModal}>Cerrar</BotonStyle>
        </ContenedorModalBasico>
      </Overlay>
      )}
    </>
  );
}

export default ModalConfirmaion