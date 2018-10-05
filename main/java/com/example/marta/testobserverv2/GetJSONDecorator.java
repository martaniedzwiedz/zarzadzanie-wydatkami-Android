package com.example.marta.testobserverv2;

abstract class GetJSONDecorator extends GetOperation{

    protected GetOperation product;
    @Override
    public void GetJson() {
    }

    public GetJSONDecorator(GetOperation product) {
        this.product = product;
    }
}
