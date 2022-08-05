import styled from 'styled-components'


export const Overlay = styled.div`
    width: 100%;
    min-height: 100%;
    height: auto;
    /* position: fixed; */
    position: absolute;
    top: 0;
    left: 0;
    background: rgba(0, 0, 0, .5);
    text-align: center;
    padding: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 999;
    /* @media screen and (max-height: 900px) {
        padding: 80px;
    } */
    &.modalDelete{
        position: fixed;
    }
`

export const ContenedorModal = styled.div`
    min-height: 600px;
    background: #fff;
    position: relative; 
    border-radius: 5px;
    box-shadow: rgba(100, 100, 111, .2) 0px 7px 29px 0px;
    padding: 40px;

    @media screen and (max-height: 1000px) {
        /* height: 820px; */
        padding: 0;
    }

    @media screen and (max-height: 850px) {
        /* height: 650px; */
        padding: 0px;
    }
`
export const CerrarModalStyle = styled.button`
    position: absolute;
    display: flex;
    justify-content: center;
    align-items: center;
    right: 30px;
    top: 20px;
	padding: 15px;
	border-radius: 50%;
	color: #fff;
	border: none;
	background: #000;
	cursor: pointer;
	font-family: 'Roboto', sans-serif;
	font-weight: 500;
	transition: background-color .3s ease ;
    &:hover {
        background: #0066FF;
    }
`
export const ContenedorModalBasico = styled.div`
    width: 512px;
    /* height: 307px; */
    min-height: 100px;
    background: #fff;
    position: relative; 
    border-radius: 25px;
    box-shadow: rgba(100, 100, 111, .2) 0px 7px 29px 0px;
    padding: 40px;
    h3{
        padding-top: 40px;
        font-size: 26px;
        line-height: 28px;
        margin-bottom: 20px;
        font-weight: bold;
    }
    p{
        font-size: 25px;
        line-height: 28px;
        margin-bottom: 50px;
        font-weight: normal
    }
    @media screen and (max-height: 800px) {
        width: 420px;
        /* height: 300px; */
        h3{
            padding-top: 20px;
            font-size: 20px;
        }
        p{
            font-size: 18px;
            margin-bottom: 20px;
        }
    }
`
export const BotonModal = styled.button`
    padding: 20px 50px;
    margin: 0 10px;
	border-radius: 5px;
	color: #fff;
	border: none;
	background: ${props =>props.deleteStyle==="eliminar"?"#3257db":"#131523"};
	cursor: pointer;
	font-family: 'Roboto', sans-serif;
	font-weight: 500;
    &:hover{
        background: ${props =>props.deleteStyle==="eliminar"?"#fd1a12":"#3f4e83"};
    }
`
export const BotonStyle = styled.button`
    padding: 10px 30px;
	border-radius: 100px;
	color: #fff;
	border: none;
	background: #1766DC;
	cursor: pointer;
	font-family: 'Roboto', sans-serif;
	font-weight: 500;
    &:hover{
        background: #0066FF;
    }
`
export const BotonOrder = styled.button`
    width: 100%;
    height: 50px;
    border-radius: 5px;
	transition: .3s ease all;
    border: 3px solid ${props => props.activado==="activadoF" ? '#0066FF' : '#a3a6b4'};
    color: ${props => props.activado==="activadoF" ? '#fff' : '#000'};
    background-color: ${props => props.activado==="activadoF" ? '#0066FF' : '#fff'};
    cursor: pointer;
    font-size: 18px;
    &:hover{
        border: 3px solid #0075FF;
    }
`
 
