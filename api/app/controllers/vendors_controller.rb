class VendorsController < ApplicationController
  before_action :set_vendor, only: [:show, :show_products]

  # GET /vendors
  # GET /vendors.json
  def index
    @vendors = Vendor.all

    render json: @vendors
  end

  # GET /vendors/1
  # GET /vendors/1.json
  def show
    render json: @vendor
  end

  # GET /vendors/1/products
  # GET /vendors/1/products.json
  def show_products
    render json: @vendor.products
  end

  private

    def set_vendor
      @vendor = Vendor.find(params[:VendorSN])
    end
end
