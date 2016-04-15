class OrdersController < ApplicationController

  include Send_API

  # GET /orders
  # GET /orders.json
  def index
    result = query_order

    if result['ErrorCode'] == 0
      render json: { :status => 0, :data => result['Order'] }
    else
      render json: { :status => 1, :data => result['ErrorMsg'] }
    end
  end

  # GET /orders/1
  # Get /orders/1.json
  def query_by_external_order_no
    result = query_order_by_external_order_no(params[:ExternalOrderNo].to_s)

    if result['ErrorCode'] == 0
      if result['Order'].empty?
        render json: { :status => 1, :data => '無此編號' }
      else
        render json: { :status => 0, :data => result['Order'][0] }
      end
    else
      render json: { :status => 1, :data => result['ErrorMsg'] }
    end
  end

  # POST /orders
  # POST /orders.json
  def order
    data = { :ExternalOrderNo => params[:ExternalOrderNo], :ProductSN => params[:ProductSN], :StyleA => params[:StyleA], :StyleB => params[:StyleB], :Quantity => params[:Quantity], :Price => params[:Price], :Amount => params[:Amount], :OrderName => params[:OrderName], :OrderAddress => params[:OrderAddress], :OrderEmail => params[:OrderEmail], :OrderPhone => params[:OrderPhone], :ConsigneeName => params[:ConsigneeName], :ConsigneeAddress => params[:ConsigneeAddress], :ConsigneeEmail => params[:ConsigneeEmail], :ConsigneePhone => params[:ConsigneePhone], :DeliverTime => params[:DeliverTime], :Result => params[:Result], :PaymentResult => params[:PaymentResult], :Param => params[:Param] }
    
    result = create_order(data)

    if result['ErrorCode'] == 0
      render json: { :status => 0, :data => 'success' }
    else
      render json: { :status => 1, :data => result['ErrorMsg'] }
    end
  end

  # POST /cancel
  # POST /cancel.json
  def cancel
    result = cancel_order(params[:ExternalOrderNo].to_s)

    if result['ErrorCode'] == 0
      render json: { :status => 0, :data => 'success' }
    else
      render json: { :status => 1, :data => result['ErrorMsg'] }
    end
  end

end
