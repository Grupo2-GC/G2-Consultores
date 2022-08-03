import React from 'react'
import { BotonStyle, ContenedorModalBasico, Overlay } from '../styles/ModalStyle';

const ModalConfirmaion = ({ modal, setModal, producto = false }) => {
  const CerrarModal = () => {
    setModal(false)
  }

  return (
    <>
      {modal && (
        <Overlay className="modalDelete">
          <ContenedorModalBasico>
            {producto ? <h3>Producto Registrado</h3> : <h3>Usuario Registrado</h3>}
            {producto ? <p>Los datos del producto se guardaron con éxito</p>
              : <p>Los datos del colaborador se guardaron con éxito</p>}
            <BotonStyle onClick={CerrarModal}>Cerrar</BotonStyle>
          </ContenedorModalBasico>
        </Overlay>
      )}
    </>
  );
}

export default ModalConfirmaion