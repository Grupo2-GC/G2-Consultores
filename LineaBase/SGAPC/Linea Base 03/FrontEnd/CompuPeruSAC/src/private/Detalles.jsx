import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Loading } from "../components/Loading";
import ModalDeleteProducto from "../components/ModalDeleteProducto";
import ModalEditProducto from "../components/ModalEditProducto";
import { clienteAxios } from "../config/axios";
import { CardContainer, CardEstado, CardInfo1, CardInfoSub, CardSala, CardSalaDetalles, ContainerBox, Hr, ImageEscritorio } from "../styles/Escritorio.Style";
import { BtnTablaEditar } from "../styles/TablasStyle";
import { ContenedorModulo, TablaContenedor, TitlePage } from "../styles/UsablesStyle";

const Detalles = () => {
  const { id } = useParams();
  const [item, setItem] = useState([]);
  const [loading, setLoading] = useState(false);
  const [modal, setModal] = useState(false);
  const [modalDelete, setModalDelete] = useState(false);

  // console.log(item);
    const modalEdit = () => {
        setModal(true);
    }

    const modalDeleteOpen = () => {
        setModalDelete(true);
    }

  useEffect(() => {
    const getData = async () => {
      const res = await clienteAxios.get(
        `/productos/${id}`
      );
      console.log(res);
      setItem(res.data);
      setLoading(true);
    };
    getData();
  }, [modal]);
  return (
    <>
      {loading ? (
        <>
        <ModalDeleteProducto modalDelete={modalDelete} setModalDelete={setModalDelete} item={item}/>
        <ModalEditProducto modal={modal} setModal={setModal} item={item}/>
          {item.nombre ? (
            <ContenedorModulo>
            <TitlePage tabla="tabla">
              <h1>Detalles del Producto</h1>
              <h4>Productos</h4>
            </TitlePage>
            <br />
            <TablaContenedor>
              <ContainerBox>
                <CardContainer>
                  <CardSalaDetalles>
                    <CardInfoSub>
                      <h4>Producto</h4>
                      <p>{item.nombre}</p>
                    </CardInfoSub>
                    <CardInfoSub>
                      <h4>Precio de Venta</h4>
                      <p>{item.precioVenta}</p>
                    </CardInfoSub>
                    <CardInfoSub>
                      <h4>Precio de Compra</h4>
                      <p>{item.precioCompra}</p>
                    </CardInfoSub>
                    <CardInfoSub>
                      <h4>Stock</h4>
                      <p>{item.stock}</p>
                    </CardInfoSub>
                    <CardInfoSub>
                      <h4>Categoria</h4>
                      <p>{item.categoria}</p>
                    </CardInfoSub>
                    <Hr />
                    <CardInfoSub>
                      <h3>Nombre</h3>
                      <p>{item.nombreProveedor}</p>
                    </CardInfoSub>
                    <CardInfoSub>
                      <h3>Telefono</h3>
                      <p>{item.numeroProveedor}</p>
                    </CardInfoSub>
                    <CardInfoSub>
                      <h3>Email</h3>
                      <p>{item.emailProveedor}</p>
                    </CardInfoSub>
                    <br />
                    <CardInfoSub>
                      <BtnTablaEditar btnColor="edit"
                      onClick={modalEdit}
                      >
                        <i className="far fa-edit iEdit"></i>
                      </BtnTablaEditar>
                      <BtnTablaEditar onClick={modalDeleteOpen}
                      >
                        <i className="far fa-trash-alt iDelete"></i>
                      </BtnTablaEditar>
                    </CardInfoSub>
                  </CardSalaDetalles>
                </CardContainer>
                <ImageEscritorio>
                  <img src={item.imgURL} alt="img" />
                </ImageEscritorio>
              </ContainerBox>
            </TablaContenedor>
          </ContenedorModulo>
          ) : (
            <div>
              Eliminado
            </div>
          )}
        </>
      ) : (
        <Loading />
      )}
    </>
  )
}

export default Detalles