import styled from 'styled-components'

export const CardContainer = styled.div`
    margin-top: 50px;
    display: grid;
    place-items: center;
    grid-template-columns: repeat(auto-fit, minmax(293px, 1fr));
`
export const CardSala = styled.div`
    width: 293px;
    height: 338px;
    background: #fff;
    border-radius: 10px;
    box-shadow: 0px 0px 25px 5px rgba(0,0,0,0.1);
    margin-bottom: 30px;
`
export const CardEstado = styled.div`
    padding: 25px;
    display: flex;
    justify-content: space-between;
    img{
        width: 61.5px;
        height: 61.5px;
    }
`
export const CardInfo1 = styled.div`
    h4{
        font-size: 18px;
        font-weight: 600;
        color: #4D4F5C;
    }
    p{
        font-size: 16px;
        font-weight: 500;
        color: #57D2A9;
        &.cancelado{
            color: red;
        }
        &.pendiente{
            color: #FEBC2D;
        }
    }
`
export const CardInfoSub = styled.div`
    padding: 5px 25px;
    display: flex;
    justify-content: space-between;
    h4{
        font-size: 16px;
        font-weight: 600;
        color: #131523;
    }
    h3{
        font-size: 16px;
        font-weight: 600;
        color: #4D4F5C;
    }
    p{
        font-size: 16px;
        font-weight: 500;
        color: #4D4F5C;
    }
`
export const BtnOn = styled.div`
    display: flex;
    gap: 20px;
    h4{
        font-size: 16px;
        font-weight: 500;
        color: #4D4F5C;
    }
`
export const CircleBox = styled.div`
    width: 20px;
    height: 20px;
    border-radius: 50%;
    border: 1px solid #21D59B;
    display: grid;
    place-content: center;
    &.cancelado{
        border: 1px solid red;
    }
    &.pendiente{
        border: 1px solid #FEBC2D;
    }
`
export const Circle = styled.div`
    width: 12px;
    height: 12px;
    background: #21D59B;
    border-radius: 50%;
    &.cancelado{
        background: red;
    }
    &.pendiente{
        background: #FEBC2D;
    }
`
export const Hr = styled.hr`
    color: #D7DBEC;
    margin: 15px 0; 
`