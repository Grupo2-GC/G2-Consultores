import React, { useEffect, useState } from "react";
import ReactPaginate from "react-paginate";
import { Link } from "react-router-dom";
import { Loading } from "../components/Loading";
import { clienteAxios } from "../config/axios";
import { BoxTable, BtnDetalle, BtnStock, CantidadResultados, PaginacionBox, PaginacionContainer, TableStyle, Tbody, Td, Th, Thead, Tr } from "../styles/TablasStyle";

const TablaProductos = () => {
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
            `/productos?page=${pagina}&limit=10`
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
    return (
        <BoxTable>
            {loading ? (
                <TableStyle>
                    <Thead>
                        <Tr>
                            <Th>
                                Precio De Venta&nbsp;&nbsp;
                            </Th>
                            <Th>
                                Precio de Compra&nbsp;&nbsp;
                            </Th>
                            <Th>
                                Nombre&nbsp;&nbsp;
                            </Th>
                            <Th>
                                Categoria&nbsp;&nbsp;
                            </Th>
                            <Th>
                                Nombre del Proveedor&nbsp;&nbsp;
                            </Th>
                            <Th>
                                Numero del Proveedor&nbsp;&nbsp;
                            </Th>
                            <Th>
                                Stock&nbsp;&nbsp;
                            </Th>
                            <Th>
                            &nbsp;&nbsp;
                            </Th>
                        </Tr>
                    </Thead>

                    {items.map((item) => {
                        return (
                            <Tbody key={item._id}>
                                <Tr>
                                    <Td data-label="Precio de Venta"> {item.precioVenta} </Td>
                                    <Td data-label="Precio de Compra"> {item.precioCompra} </Td>
                                    <Td data-label="Nombre del Producto"> {item.nombre} </Td>
                                    <Td data-label="Categoria"> {item.categoria} </Td>
                                    <Td data-label="Nombre de Proveedor"> {item.nombreProveedor} </Td>
                                    <Td data-label="Numero del Proveedor"> {item.numeroProveedor} </Td>
                                    <Td data-label="Stock">
                                        <BtnStock
                                            className={
                                                item.stock !== 0 ? "stock" : "sin_stock"
                                            }
                                        >
                                            {item.stock !== 0 ? "Disponible" : "No Disponible"}
                                        </BtnStock>
                                    </Td>
                                    <Td>
                                        <Link to={`detalles/${item._id}`}><BtnDetalle>VER DETALLE</BtnDetalle></Link>
                                    </Td>
                                </Tr>
                            </Tbody>
                        );
                    })}
                </TableStyle>
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

export default TablaProductos
