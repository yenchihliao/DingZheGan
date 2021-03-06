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
    id = params[:ExternalOrderNo]
    if Unionpay.exists?(ExternalOrderNo: id)
      data = JSON.parse(Unionpay.find_by_ExternalOrderNo(id).to_json)

      result = query_order_by_external_order_no(data['orderno'])

      if result['ErrorCode'] == 0
        if result['Order'].empty?
          render json: { :status => 1, :data => '無此編號' }
        else
          render json: { :status => 0, :data => result['Order'][0] }
        end
      else
        render json: { :status => 1, :data => result['ErrorMsg'] }
      end
    else
      render json: { :status => 1, :data => '無此編號' }
    end
  end

  # POST /orders
  # POST /orders.json
  def order
    Order.create(:ExternalOrderNo => params[:ExternalOrderNo], :ProductSN => params[:ProductSN], :StyleA => params[:StyleA], :StyleB => params[:StyleB], :Quantity => params[:Quantity], :Price => params[:Price], :Amount => params[:Amount], :OrderName => params[:OrderName], :OrderAddress => params[:OrderAddress], :OrderEmail => params[:OrderEmail], :OrderPhone => params[:OrderPhone], :ConsigneeName => params[:ConsigneeName], :ConsigneeAddress => params[:ConsigneeAddress], :ConsigneeEmail => params[:ConsigneeEmail], :ConsigneePhone => params[:ConsigneePhone], :DeliverTime => params[:DeliverTime], :Result => params[:Result], :PaymentResult => params[:PaymentResult], :Param => params[:Param])

    render json: { :status => 0, :data => 'success' }
  end

  # POST /cancel
  # POST /cancel.json
  def cancel
    result = cancel_order(params[:ExternalOrderNo])

    if result['ErrorCode'] == 0
      render json: { :status => 0, :data => 'success' }
    else
      render json: { :status => 1, :data => result['ErrorMsg'] }
    end
  end

end
