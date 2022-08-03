import React from 'react'
import { ContenedorModulo, TablaContenedor, TitlePage } from '../styles/UsablesStyle'
import TablaOrdenes from '../tables/TablaOrdenes'

const ListadoOrdenes = () => {
  return (
    <>
      <ContenedorModulo>
        <TitlePage tabla="tabla">
          <h1>Listado de Ordenes</h1>
          <h4>Gestión Ordenes</h4>
        </TitlePage>
        <br />
        <TablaContenedor>
          <TablaOrdenes />
        </TablaContenedor>
      </ContenedorModulo>
    </>
  )
}

export default ListadoOrdenes
