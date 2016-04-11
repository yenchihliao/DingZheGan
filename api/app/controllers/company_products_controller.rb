class CompanyProductsController < ApplicationController
  before_action :find_company

  # GET /companies/1/products
  # GET /companies/1/products.json
  def index
    @products = @company.products

    render json: @products
  end

  # GET /companies/1/products/1
  # GET /companies/1/products/1.json
  def show
    @product = @company.products.find(params[:id])

    render json: @product
  end

  private

    def find_company
      @company = Company.find(params[:company_id])
    end

    def product_params
      params.require(:product).permit(:number, :name, :price, :description)
    end
end
