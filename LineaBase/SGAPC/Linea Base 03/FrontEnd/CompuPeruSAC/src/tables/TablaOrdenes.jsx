import React, { useEffect, useState } from "react";
import ReactPaginate from "react-paginate";
import { Link } from "react-router-dom";
import { Loading } from "../components/Loading";
import { NoData } from "../components/NoData";
import { clienteAxios } from "../config/axios";
import { BtnOn, CardContainer, CardEstado, CardInfo1, CardInfoSub, CardSala, Circle, CircleBox, Hr } from "../styles/OrdenesStyle";
import { BoxTable, BtnDetalle, BtnStock, CantidadResultados, PaginacionBox, PaginacionContainer, TableStyle, Tbody, Td, Th, Thead, Tr } from "../styles/TablasStyle";
import SalaVerde from "../assets/svg/SALA_VERDE.svg";
import SalaAmarilla from "../assets/svg/SALA_AMARILLA.svg";
import SalaRojo from "../assets/svg/SALA_ROJO.svg";

const TablaOrdenes = () => {
    const [items, setItems] = useState([]);
    const [loading, setLoading] = useState(false);
    const [pageTotal, setPageTotal] = useState(0);
    const [page, setPage] = useState(1)
    const [totalDatos, setTotalDatos] = useState(0)

    useEffect(() => {
        getData(page);
    }, [page]);
    const getData = async (pagina = 1) => {
        const res = await clienteAxios.get(
            `/productos?page=${pagina}&limit=12`
        );
        // console.log(res.data);
        setItems(res.data.docs);
        setPageTotal(res.data.totalPages)
        setLoading(true);
        setTotalDatos(res.data.totalDocs)
        // return res.data.results;
    };
    const handlePageClick = (data) => {
        setLoading(false);
        let pagina = data.selected + 1;
        // console.log(pagina);
        setPage(pagina);
        getData(pagina);
    };
    const totalDePaginas = pageTotal;
    console.log(items);
    return (
        <BoxTable>
            {loading ? (
                <>
                    {totalDatos === 0 ? (
                        <NoData key="nodata" />
                    ) : (
                        <CardContainer>
                            {items.map((item) => {
                                return (
                                    <CardSala key={item._id}>
                                        <CardEstado>
                                            {item.stock < 6 && <img src={SalaRojo} alt="estado" />}
                                            {item.stock < 11 && item.stock > 5 && <img src={SalaAmarilla} alt="estado" />}
                                            {item.stock > 10 && <img src={SalaVerde} alt="estado" />}
                                            <CardInfo1>
                                                <h4>Order-{item._id.substring(18, 24)} </h4>
                                                {item.stock < 6 && <p className="cancelado"> Cancelado </p>}
                                                {item.stock < 11 && item.stock > 5 && <p className="pendiente"> Pendiente </p>}
                                                {item.stock > 10 && <p> Completado </p>}
                                            </CardInfo1>
                                        </CardEstado>
                                        <CardInfoSub>
                                            <h4>Codigo</h4>
                                            <p>{item._id}</p>
                                        </CardInfoSub>
                                        <CardInfoSub>
                                            <h4>Fecha</h4>
                                            <p>{item.registro.substring(0,10)}</p>
                                        </CardInfoSub>
                                        <CardInfoSub>
                                            <h3>Estado</h3>
                                            {item.stock < 6 && <>
                                                <BtnOn>
                                                    <CircleBox className="cancelado">
                                                        <Circle className="cancelado"></Circle>
                                                    </CircleBox>
                                                    <h4>Cancelado</h4>
                                                </BtnOn>
                                            </>}
                                            {item.stock < 11 && item.stock > 5 && <>
                                                <BtnOn>
                                                    <CircleBox className="pendiente">
                                                        <Circle className="pendiente"></Circle>
                                                    </CircleBox>
                                                    <h4>Pendiente</h4>
                                                </BtnOn>
                                            </>}
                                            {item.stock > 10 && <>
                                                <BtnOn>
                                                    <CircleBox>
                                                        <Circle></Circle>
                                                    </CircleBox>
                                                    <h4>Completado</h4>
                                                </BtnOn>
                                            </>}
                                        </CardInfoSub>
                                        <Hr />
                                        <CardInfoSub>
                                            <h3>Producto</h3>
                                            <p>{item.nombre}</p>
                                        </CardInfoSub>
                                        <CardInfoSub>
                                            <h3>Categoria</h3>
                                            <p>{item.categoria}</p>
                                        </CardInfoSub>
                                        <CardInfoSub>
                                            <h3>Precio</h3>
                                            <p>S/.{item.precioVenta}</p>
                                        </CardInfoSub>
                                    </CardSala>
                                )
                            })}
                        </CardContainer>
                    )}
                </>
                // <TableStyle>
                //     <Thead>
                //         <Tr>
                //             <Th>
                //                 Precio De Venta&nbsp;&nbsp;
                //             </Th>
                //             <Th>
                //                 Precio de Compra&nbsp;&nbsp;
                //             </Th>
                //             <Th>
                //                 Nombre&nbsp;&nbsp;
                //             </Th>
                //             <Th>
                //                 Categoria&nbsp;&nbsp;
                //             </Th>
                //             <Th>
                //                 Nombre del Proveedor&nbsp;&nbsp;
                //             </Th>
                //             <Th>
                //                 Numero del Proveedor&nbsp;&nbsp;
                //             </Th>
                //             <Th>
                //                 Stock&nbsp;&nbsp;
                //             </Th>
                //             <Th>
                //             &nbsp;&nbsp;
                //             </Th>
                //         </Tr>
                //     </Thead>

                //     {items.map((item) => {
                //         return (
                //             <Tbody key={item._id}>
                //                 <Tr>
                //                     <Td data-label="Precio de Venta"> {item.precioVenta} </Td>
                //                     <Td data-label="Precio de Compra"> {item.precioCompra} </Td>
                //                     <Td data-label="Nombre del Producto"> {item.nombre} </Td>
                //                     <Td data-label="Categoria"> {item.categoria} </Td>
                //                     <Td data-label="Nombre de Proveedor"> {item.nombreProveedor} </Td>
                //                     <Td data-label="Numero del Proveedor"> {item.numeroProveedor} </Td>
                //                     <Td data-label="Stock">
                //                         <BtnStock
                //                             className={
                //                                 item.stock !== 0 ? "stock" : "sin_stock"
                //                             }
                //                         >
                //                             {item.stock !== 0 ? "Disponible" : "No Disponible"}
                //                         </BtnStock>
                //                     </Td>
                //                     <Td>
                //                         <Link to={`detalles/${item._id}`}><BtnDetalle>VER DETALLE</BtnDetalle></Link>
                //                     </Td>
                //                 </Tr>
                //             </Tbody>
                //         );
                //     })}
                // </TableStyle>
            ) : (
                <Loading />
            )}

            <PaginacionContainer>
                <CantidadResultados>
                    {items.length === 0 ? (
                        <h3>"----"</h3>
                    ) : <h3> Mostrando 1 - {items.length} de {totalDatos} resultados </h3>}
                </CantidadResultados>
                <PaginacionBox>
                    <ReactPaginate
                        previousLabel={<i className="fas fa-angle-left"></i>}
                        nextLabel={<i className="fas fa-angle-right"></i>}
                        breakLabel={"..."}
                        pageCount={totalDePaginas}
                        marginPagesDisplayed={3}
                        // pageRangeDisplayed={6}
                        onPageChange={handlePageClick}
                        containerClassName={"pagination"}
                        previousClassName={"anterior"}
                        nextClassName={"siguiente"}
                        activeLinkClassName={"activePaginacionFondo"}
                        activeClassName={"activePaginacionFondo"}
                    />
                </PaginacionBox>
            </PaginacionContainer>
        </BoxTable>
    )
}

export default TablaOrdenes
