class UnionpaysController < ApplicationController

  include Send_API

  # GET /feedbacks/1
  # GET /feedbacks/1.json
  def feedbacks
    id = params[:ExternalOrderNo].to_i
    puts id
    if Unionpay.exists?(ExternalOrderNo: id)
      result = JSON.parse(Unionpay.find(id).to_json)

      result.delete('created_at')
      result.delete('updated_at')
      
      render json: { :status => 0, :data => result }
    else
      render json: { :status => 1, :data => '無此編號' }
    end
  end

  # POST /
  def union_pay
    key = md5_generate params[:scode] + params[:orderno] + params[:orderid] + params[:amount] + params[:currcode] + params[:status] + params[:respcode] + '12345678'

    if params[:scode] != 'EID006501' or key != params[:sign]
      return
    end

    Unionpay.create(:ExternalOrderNo => params[:orderid], :orderno => params[:orderno], :amount => params[:amout].to_f, :currcode => params[:currcode], :memo => params[:memo], :resptime => params[:resptime], :status => params[:status].to_i, :respcode => params[:respcode], :rmbrate => params[:rmbrate].to_f, :sign => params[:sign])

    id = params[:orderid]
    if Order.exists?(ExternalOrderNo: id)
      data = JSON.parse(Order.find_by_ExternalOrderNo(id).to_json)
      
      data.delete('created_at')
      data.delete('updated_at')

      data['ExternalOrderNo'] = params[:orderno]
      data['Param'] = params[:currcode] + ',' + params[:resptime] + ',' + params[:rmbrate]

      if params[:status].to_i == 1
        data['Result'] = 1
        data['PaymentResult'] = 1
      else
        data['Result'] = 2
        data['PaymentResult'] = 2
      end

      create_order(data)

      head :no_content
    end
  end

end
