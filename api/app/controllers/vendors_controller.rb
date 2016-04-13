class VendorsController < ApplicationController
  before_action :set_vendor, only: [:show, :show_products]

  # GET /vendors
  # GET /vendors.json
  def index
    vendors = JSON.parse(Vendor.all.to_json)

    vendors.collect! do |vendor|
      vendor.delete('created_at')
      vendor.delete('updated_at')
      vendor
    end

    render json: { :status => 0, :data => vendors }
  end

  # GET /vendors/1
  # GET /vendors/1.json
  def show
    if @vendor.nil?
      render json: { :status => 1, :data => '無此編號' }
    else
      vendor = JSON.parse(@vendor.to_json)

      vendor.delete('created_at')
      vendor.delete('updated_at')

      render json: { :status => 0, :data => vendor }
    end
  end

  # GET /vendors/1/products
  # GET /vendors/1/products.json
  def show_products
    if @vendor.nil?
      render json: { :status => 1, :data => '無此編號' }
    else
      products = JSON.parse(@vendor.products.to_json)

      products.collect! do |product|
        product.delete('created_at')
        product.delete('updated_at')
        product
      end

      render json: { :status => 0, :data => products }
    end
  end

  private

    def set_vendor
      if Vendor.exists?(params[:VendorSN])
        @vendor = Vendor.find(params[:VendorSN])
      else
        @vendor = nil
      end
    end
end
