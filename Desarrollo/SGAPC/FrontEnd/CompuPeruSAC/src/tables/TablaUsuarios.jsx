import React, { useContext, useEffect, useState } from "react";
import { Loading } from "../components/Loading";
import { ColaboradorContext } from "../context/colaboradores/ColaboradorProvider";
import ReactPaginate from "react-paginate";
// import { ModaleEdit } from "../components/ModaleEdit";
// import { ModalDelete } from "../components/ModalDelete";
import {
    BoxTable,
    TableStyle,
    Tbody,
    Td,
    Th,
    Thead,
    Tr,
    BtnTablaEditar,
    PaginacionContainer,
    CantidadResultados,
    PaginacionBox,
} from "../styles/TablasStyle";
import { NoData } from "../components/NoData";
export const TablaUsuarios = () => {
    const {
        colaboradores,
        totalPages,
        cargando,
        obtenerColaboradores,
        ColaboradorActual,
        resetLoading,
        actualizar,
        totalDatos
    } = useContext(ColaboradorContext);

    const [modal, setModal] = useState(false);
    const [modalDelete, setModalDelete] = useState(false);
    const [page, setPage] = useState(1)

    useEffect(() => {
        resetLoading()
        obtenerColaboradores(page);
    }, [page, totalPages]);



    const handlePageClick = (data) => {
        resetLoading()
        let pagina = data.selected + 1;
        setPage(pagina);
    };
    /*Calcular total para reactPaginate*/
    const totalDePaginas = totalPages;

    const modalEdit = (colaborador) => {
        setModal(true);
        actualizar(colaborador);
        ColaboradorActual(colaborador)
    }

    const modalDeleteOpen = (item) => {
        setModalDelete(true);
        ColaboradorActual(item)
    }

    return (
        <>
            {/* <ModaleEdit  modal={modal} setModal={setModal}  />
    <ModalDelete modalDelete={modalDelete} setModalDelete={setModalDelete} /> */}
            <BoxTable>
                {!cargando ? (
                    <>
                        <TableStyle>
                            <Thead>
                                <Tr>
                                    <Th>
                                        Nombres y Apellidos&nbsp;&nbsp;
                                    </Th>
                                    <Th>
                                        DNI&nbsp;&nbsp;
                                    </Th>
                                    <Th>
                                        Genero&nbsp;&nbsp;
                                    </Th>
                                    <Th>
                                        Edad&nbsp;&nbsp;
                                    </Th>
                                    <Th>
                                        Empresa&nbsp;&nbsp;
                                    </Th>
                                    <Th>
                                        Perfil&nbsp;&nbsp;
                                    </Th>
                                    <Th>
                                        Fecha de Ingreso&nbsp;&nbsp;
                                    </Th>
                                    <Th>
                                        Fecha de Salida&nbsp;&nbsp;
                                    </Th>
                                    <Th>
                                        Editar&nbsp;&nbsp;
                                    </Th>
                                </Tr>
                            </Thead>

                            {colaboradores.map((item) => {
                                return (
                                    <Tbody key={item._id}>
                                        <Tr>
                                            <Td data-label="Nombre y Apellidos"> {item.first_name} {item.last_name} </Td>
                                            <Td data-label="DNI"> {item.dni} </Td>
                                            <Td data-label="Genero"> {item.gender} </Td>
                                            <Td data-label="Edad"> {item.age} </Td>
                                            <Td data-label="Area"> {item.enterprise} </Td>
                                            <Td data-label="Perfil"> {item.profile} </Td>
                                            <Td data-label="Fecha de Ingreso"> {item.admission_date} </Td>
                                            <Td data-label="Fecha de Salida"> {item.departure_date} </Td>
                                            <Td data-label="Editar">
                                                <BtnTablaEditar btnColor="edit"
                                                    onClick={() => modalEdit(item)}>
                                                    <i className="far fa-edit iEdit"></i>
                                                </BtnTablaEditar>
                                                <BtnTablaEditar 
                                                    onClick={() => modalDeleteOpen(item)}>
                                                    <i className="far fa-trash-alt iDelete"></i>
                                                </BtnTablaEditar>
                                            </Td>
                                        </Tr>
                                    </Tbody>
                                );
                            })}
                        </TableStyle>
                        {colaboradores.length === 0 && <NoData />}
                    </>
                ) : (
                    <Loading />
                )}

                <PaginacionContainer>
                    <CantidadResultados>
                        {colaboradores.length === 0 ? (
                            <h3>"----"</h3>
                        ) : <h3> Mostrando 1 - {colaboradores.length} de {totalDatos} resultados </h3>}
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
        </>
    );
};
