require 'net/http'
require 'digest'

class ProductsController < ApplicationController
  before_action :set_product, only: []

  # GET /products
  # GET /products.json
  def index
    @products = Product.all

    render json: @products
  end

  # GET /products/1
  # GET /products/1.json
  def show
    render json: set_product(params[:id].to_i)
    # render json: @product
  end

  # POST /products
  # POST /products.json
  def update
  end

  private

    def set_product
      @product = Product.find(params[:id])
    end

    def get_product(id)
      unix_time = Time.now.to_i

      md5 = Digest::MD5.new
      md5.update unix_time.to_s + 'kikirace'
      key = md5.hexdigest

      uri = URI('http://kikistore.csmuse.com/kikistore/api/kikirace_getProductDetail.php')
      params = { :ProductSN => id, :Language => 2, :Time => unix_time, :Key => key}
      uri.query = URI.encode_www_form(params)

      res = Net::HTTP.get(uri).force_encoding("UTF-8")
      hash = JSON.parse(res[2..-1].to_s)
    end
end
