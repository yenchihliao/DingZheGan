class UnionpaysController < ApplicationController

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
    if params[:scode] == 'EID006501'
      u = Unionpay.create(:orderid => params[:orderid], :ExternalOrderNo => params[:orderno], :amount => params[:amout].to_f, :currcode => params[:currcode], :memo => params[:memo], :resptime => params[:resptime], :status => params[:status].to_i, :respcode => params[:respcode], :rmbrate => params[:rmbrate].to_f, :sign => params[:sign])
    end
      
    head :no_content
  end

end
