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
    render json: create_update_product(params[:ProductSN].to_i)
  end

  # POST /products
  # POST /products.json
  def update
    parse_products
  end

  # POST /products/1
  # POST /products/1.json
  def single_update
    create_update_product(params[:ProductSN].to_i)
  end

  private

    def set_product
      @product = Product.find(params[:ProductSN])
    end

    def create_update_vendor(id, name)
      if Vendor.exists?(VendorSN: id)
      else
        Vendor.create(:VendorSN => id, :VendorTitle => name)
      end
    end

    def create_update_product(id, vendorName = '')
      result = get_product(id)

      unless result['ErrorCode'] == 0 && !result['Product'].empty?
        return { :status => 1, :data => result['ErrorMsg'] }
      end

      hash = result['Product'][0]

      if Product.exists?(ProductSN: id)
        p = Product.find(id)
        p.update(:ProductSN => id, :ProductVendor => hash['ProductVendor'], :ProductTitle => hash['ProductTitle'], :ProductNo => hash['ProductNo'], :SellPrice => hash['SellPrice'], :SellPriceCNY => hash['SellPriceCNY'], :ProductQuantity => hash['ProductQuantity'], :ProductIntroduction => hash['ProductIntroduction'], :StyleTitleA => hash['StyleTitleA'], :StyleTitleB => hash['StyleTitleB'], :LargeIcon => hash['LargeIcon'], :SmallIcon => hash['SmallIcon'], :ProductPhoto1 => hash['ProductPhoto1'], :ProductPhoto2 => hash['ProductPhoto2'], :ProductPhoto3 => hash['ProductPhoto3'], :ProductPhoto4 => hash['ProductPhoto4'], :ProductPhoto5 => hash['ProductPhoto5'], :ProductPhoto6 => hash['ProductPhoto6'], :ProductPhoto7 => hash['ProductPhoto7'], :ProductPhoto8 => hash['ProductPhoto8'])
      else
        create_update_vendor(hash['ProductVendor'], vendorName)
        Product.create(:ProductSN => id, :ProductVendor => hash['ProductVendor'], :ProductTitle => hash['ProductTitle'], :ProductNo => hash['ProductNo'], :SellPrice => hash['SellPrice'], :SellPriceCNY => hash['SellPriceCNY'], :ProductQuantity => hash['ProductQuantity'], :ProductIntroduction => hash['ProductIntroduction'], :StyleTitleA => hash['StyleTitleA'], :StyleTitleB => hash['StyleTitleB'], :LargeIcon => hash['LargeIcon'], :SmallIcon => hash['SmallIcon'], :ProductPhoto1 => hash['ProductPhoto1'], :ProductPhoto2 => hash['ProductPhoto2'], :ProductPhoto3 => hash['ProductPhoto3'], :ProductPhoto4 => hash['ProductPhoto4'], :ProductPhoto5 => hash['ProductPhoto5'], :ProductPhoto6 => hash['ProductPhoto6'], :ProductPhoto7 => hash['ProductPhoto7'], :ProductPhoto8 => hash['ProductPhoto8'])
      end

      { :status => 0, :data => hash }
    end

    def parse_products
      file = File.join(Rails.root, 'lib', 'assets', 'products')
      File.readlines(file).each do |line|
        columns = line.split(' ')
        vendorSN = columns[0].to_i
        vendorTitle = columns[1]
        productSN = columns[2].to_i

        create_update_product(productSN, vendorTitle)
      end
    end
end
