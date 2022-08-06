// import { motion } from 'framer-motion';
import styled from 'styled-components';

export const ContenedorSistema = styled.div`
position: relative;
width: 100%;
height: 100%;
background-image: linear-gradient(to bottom, #0058ff, #797eff, #ada7ff, #d8d2ff, #ffffff);
`
export const BoxSistema = styled.div`
  min-height: 100vh;
  height: 100%;
`
export const SidebarStyle = styled.div`
  position: fixed;
  height: 100%;
  z-index: 9;
  padding-top: 30px;
  background: #0438af;
  width: 268px;
  left: ${props => props.activar ? '-268px' : '0'};
  transition: all 1s ease-in-out;
  @media screen and (max-width: 800px) {
        width: 100%;
        height: 100%;
        position: absolute;
        left: ${props => props.activar ? '0' : '-100%'};
    }
  ul{
    display: block;
    color:#ffffff;
    text-align: center;
    text-decoration: none;
  }
  img{
    display: block;
    margin: 0 auto;
    height: 45px;
    object-fit: cover;
  }
`
export const Paginas = styled.div`
  margin-left: ${props => props.activar ? '0' : '268px'};
  background: #F5F6FA;
  height: 100%;
  transition: all 1s ease-in-out;
  width: auto;
  height: 100vh;
  @media screen and (max-width: 800px){
        margin-left: 0;
    }
`
export const ContenedorPages = styled.div`
  background: #F5F6FA;
  padding: 30px 50px;
  /* min-width: 950px; */
  @media screen and (max-width: 1500px){
    padding: 30px 30px;
  }
`
export const BoxLogo = styled.div`
    @media screen and (max-width: 800px){
        display: flex;
        padding: 0px 30px;
        justify-content: space-between;
    }
`
export const BtnMobile = styled.div`
    display: none;
    @media screen and (max-width: 800px){
        display: flex;
    }
`